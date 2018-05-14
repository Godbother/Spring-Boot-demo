package com.bishe.bishe.controller;

import com.bishe.bishe.MyReader;
import com.bishe.bishe.admin.ClientConst;
import com.bishe.bishe.es.BaseCRUD;
import com.bishe.bishe.es.EsDao;
import com.bishe.bishe.model.User;
import com.bishe.bishe.model.esmodel.EsWarc;
import com.bishe.bishe.service.UserService;
import com.bishe.bishe.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EsDao esDao;

    @RequestMapping("/showuser/{id}")
    @ResponseBody
    public User selectUser(@PathVariable int id) {
        return userService.findUserById(id);
    }

    @RequestMapping(value = "/{username}/{password}/userlogin", method = RequestMethod.GET)
    @ResponseBody
    public String login(@PathVariable("username") String username,
                        @PathVariable("password") String password) {
        if (userService.login(username, password)) {
            return "ojbk";
        } else {
            return "get out!";
        }
    }

    @RequestMapping(value = "/{username}/{password1}/{password2}/regist", method = RequestMethod.POST)
    @ResponseBody
    public String regist(@PathVariable("username") String username,
                         @PathVariable("password1") String password1,
                         @PathVariable("password2") String password2) {
        if (!password1.equals(password2)) {
            return "两次密码输入不同";
        } else {
            boolean flag = userService.regist(username, password1);
            if (flag) {
                return "注册成功";
            } else {
                return "用户名已存在";
            }
        }
    }

    @RequestMapping(value = "/{id}/userdelete", method = RequestMethod.GET)
    @ResponseBody
    public String regist(@PathVariable("id") Integer id) {
        if (userService.delUser(id)) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }

    @RequestMapping(value = "/{username}/{password}/resetps", method = RequestMethod.POST)
    @ResponseBody
    public String resetPassword(@PathVariable("username") String username,
                                @PathVariable("password") String password) {
        boolean sign = userService.resetPs(username, password);
        if (sign) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

    //未测试
    @RequestMapping(value = "/{id}/{password}/{sex}/{uploadHistory}/{remark}/changeinfo", method = RequestMethod.POST)
    @ResponseBody
    public String changeInfo(@PathVariable("id") Integer id,
                             @PathVariable("password") String password,
                             @PathVariable("sex") String sex,
                             @PathVariable("uploadHistory") String uploadHistory,
                             @PathVariable("remark") String remark) {
        User user = new User();
        user.setSex(sex);
        user.setPassword(password);
        user.setId(id);
        user.setRemark(remark);
        user.setUploadHistory(uploadHistory);
        boolean sign = userService.changeInfo(user);
        if (sign) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadWarcFile(@RequestParam("file") MultipartFile file,
                                 HttpServletRequest request){

        String contentType = file.getContentType();
        String fileName = UUID.randomUUID() + file.getOriginalFilename();
        String filePath = request.getSession().getServletContext().getRealPath("uploadfile/");
        try {
            FileUtil.uploadFile(file.getBytes(),filePath, fileName);
            EsWarc esWarc = MyReader.getMyRecord(filePath + "/" + fileName);
            BaseCRUD.indexDocument(esWarc, ClientConst.index,ClientConst.type);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //返回json
        return "uploadimg success";
    }

}
