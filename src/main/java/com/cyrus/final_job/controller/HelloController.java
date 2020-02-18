package com.cyrus.final_job.controller;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.utils.Results;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Result hello(HttpServletRequest request) {
        System.out.println(request.getParameter("username"));
        return Results.createOk("hello");
    }
}
