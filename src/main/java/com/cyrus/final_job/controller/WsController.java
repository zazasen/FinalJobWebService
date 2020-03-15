package com.cyrus.final_job.controller;

import com.cyrus.final_job.entity.condition.ChatMessageCondition;
import com.cyrus.final_job.entity.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class WsController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handleMessage(Authentication authentication, ChatMessageCondition condition) {
        User user = (User) authentication.getPrincipal();
        condition.setFrom(user.getUsername());
        condition.setFromRealName(user.getRealName());
        condition.setDate(new Date());
        simpMessagingTemplate.convertAndSendToUser(condition.getTo(), "/queue/chat", condition);
    }
}
