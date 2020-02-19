package com.cyrus.final_job.controller.system.access;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.system.MenuRoleService;
import com.cyrus.final_job.service.system.MenuService;
import com.cyrus.final_job.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/access/role")
public class RoleManagerController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRoleService menuRoleService;

    @PostMapping("/getAllRoles")
    public ResultPage getAllRoles(@RequestBody JSONObject params) {
        return roleService.getAllRoles(params);
    }

    @GetMapping("/getAllMenuWithChildren")
    public Result getAllMenuWithChildren() {
        return menuService.getAllMenuWithChildren();
    }

    @PostMapping("/addRoleWithMenu")
    public Result addRoleWithMenu(@RequestBody JSONObject params){
        return roleService.addRoleWithMenu(params);
    }

    @PostMapping("/getMenuIdsByRoleId")
    public Result getMenuIdsByRoleId(@RequestBody JSONObject params){
        return menuRoleService.getMenuIdsByRoleId(params);
    }

    @PostMapping("/delRoleById")
    public Result delRoleById(@RequestBody JSONObject params){
        return roleService.deleteById(params);
    }

    @PostMapping("/delMulByIds")
    public Result delMulByIds(@RequestBody JSONObject params){
        return roleService.delMulByIds(params);
    }

    @PostMapping("/updateRole")
    public Result updateRole(@RequestBody JSONObject params){
        return roleService.updateRole(params);
    }

    @PostMapping("/query")
    public ResultPage query(@RequestBody JSONObject params){
        return roleService.query(params);
    }
}
