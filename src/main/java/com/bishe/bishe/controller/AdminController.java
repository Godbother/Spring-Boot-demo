package com.bishe.bishe.controller;

import com.bishe.bishe.model.Admin;
import com.bishe.bishe.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/{adminname}/{password}/adminlogin")
    public String login(@PathVariable("adminname")String adminname,
                        @PathVariable("password")String password,
                        HttpServletRequest request){
        HttpSession session = request.getSession();
        Admin admin = adminService.login(adminname, password);
        if (admin!=null) {
            session.setAttribute("adminid",admin.getId());
            session.setAttribute("adminname",admin.getAdminname());
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
