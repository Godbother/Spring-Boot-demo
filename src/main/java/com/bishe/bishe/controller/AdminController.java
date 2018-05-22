package com.bishe.bishe.controller;

import com.bishe.bishe.admin.MySession;
import com.bishe.bishe.model.Admin;
import com.bishe.bishe.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private MySession mySession;

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

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public String logout() {
        mySession.removeAttr("adminid");
        mySession.removeAttr("adminname");
        return "注销成功，按确认返回主页";
    }
}
