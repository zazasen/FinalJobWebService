package com.cyrus.final_job.utils;

public class RedisKeys {

    /**
     * 用户菜单列表
     *
     * @param userId
     * @return
     */
    public static String menusKey(Integer userId) {
        return "menu:userId:" + userId;
    }

    /**
     * 聊天对象不在时存放消息的key
     *
     * @param username
     * @return
     */
    public static String chatKey(String username) {
        return "chat:userId:" + username;
    }
}
