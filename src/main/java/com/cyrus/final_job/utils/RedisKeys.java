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
}
