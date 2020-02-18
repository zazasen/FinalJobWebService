package com.cyrus.final_job.controller.system;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/config")
public class SystemController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menu")
    public Result getAllMenus(){
        return menuService.getMenusByUserId();
    }
}
