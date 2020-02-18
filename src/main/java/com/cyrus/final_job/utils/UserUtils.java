package com.cyrus.final_job.utils;

import com.cyrus.final_job.entity.system.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {


    public static int getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public static User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
