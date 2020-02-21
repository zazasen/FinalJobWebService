package com.cyrus.final_job.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CommonUtils {

    public static String getPassword(String username) {
        return new BCryptPasswordEncoder().encode(username);
    }
}
