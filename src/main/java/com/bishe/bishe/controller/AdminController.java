package com.bishe.bishe.controller;

import com.bishe.bishe.model.Admin;
import com.bishe.bishe.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/{adminname}/{password}/adminlogin")
    public String login(@PathVariable("adminname")String adminname,
                        @PathVariable("password")String password){
        boolean sign = adminService.login(adminname, password);
        if (sign) {
            return "登录成功";
        }else {
            return "密码错误，请重新登录";
        }
    }

    @RequestMapping("/{id}/delwarc")
    public boolean delwarc(@PathVariable("id")String id){
        return adminService.delwarc(id);
    }
}
