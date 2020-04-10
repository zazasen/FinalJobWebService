package com.cyrus.final_job.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.config.WebSocketServer;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.condition.ChatMessageCondition;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.service.system.UserService;
import com.cyrus.final_job.utils.RedisKeys;
import com.cyrus.final_job.utils.RedisUtils;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/getAllUsers")
    public Result getAllUsers() {
        return userService.getAllUsersExceptOne();
    }

    @PostMapping("/push")
    public void pushToWeb(@RequestBody JSONObject params) throws IOException {
        ChatMessageCondition condition = params.toJavaObject(ChatMessageCondition.class);
        User user = UserUtils.getCurrentUser();
        condition.setFrom(user.getUsername());
        condition.setFromRealName(user.getRealName());
        condition.setDate(new Date());
        boolean bol = WebSocketServer.sendInfo(condition);
        // 如果发送对象目前处于离线状态
        if (!bol) {
            String key = RedisKeys.chatKey(condition.getTo());
            redisUtils.lPush(key, JSONObject.toJSONString(condition));
        }
    }
}
