package com.cyrus.final_job.config;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.condition.ChatMessageCondition;
import com.cyrus.final_job.utils.RedisKeys;
import com.cyrus.final_job.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


/**
 * WebSocketServer
 */
@ServerEndpoint("/ws/ep/{username}")
@Component
public class WebSocketServer {

    private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。一个用户对应一个 WebSocketServer
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收username
     */
    private String username = "";

    private static RedisUtils redisUtils;

    /**
     * 此处是解决无法注入的关键
     */
    private static ApplicationContext applicationContext;


    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
        WebSocketServer.redisUtils = applicationContext.getBean("redisUtils", RedisUtils.class);
    }


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        this.username = username;
        if (webSocketMap.containsKey(username)) {
            webSocketMap.remove(username);
            webSocketMap.put(username, this);
            sendOutMessage(username);
        } else {
            webSocketMap.put(username, this);
            sendOutMessage(username);
            //在线数加1
            addOnlineCount();
        }
        logger.info("用户连接:" + username + ",当前在线人数为:" + getOnlineCount());
    }

    private void sendOutMessage(String username) {
        String key = RedisKeys.chatKey(username);
        while (redisUtils.size(key) != 0) {
            String res = redisUtils.rPop(key);
            ChatMessageCondition condition = JSONObject.parseObject(res, ChatMessageCondition.class);
            try {
                if (!sendInfo(condition)) {
                    redisUtils.rPush(key, res);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(username)) {
            webSocketMap.remove(username);
            //从set中删除
            subOnlineCount();
        }
        logger.info("用户退出:" + username + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("用户消息:" + username + ",报文:" + message);
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     *
     * @param condition condition
     * @return true 发送方在线, false 发送方不在线,需要存redis
     * @throws IOException
     */
    public static boolean sendInfo(ChatMessageCondition condition) throws IOException {
        logger.info("消息:{}", condition);
        // 发送对象在线直接发送
        if (webSocketMap.containsKey(condition.getTo())) {
            webSocketMap.get(condition.getTo()).sendMessage(JSONObject.toJSONString(condition));
            return true;
        } else {
            // 不在线存在redis里,等在线了发送
            logger.error("用户" + condition.getTo() + ",不在线！");
            return false;
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}