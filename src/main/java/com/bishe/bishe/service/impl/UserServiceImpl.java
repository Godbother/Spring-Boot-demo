package com.bishe.bishe.service.impl;

import com.bishe.bishe.mapper.UserMapper;
import com.bishe.bishe.model.User;
import com.bishe.bishe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(Integer id) {
        return userMapper.selectUser(id);
    }

    @Override
    public boolean login(String username, String password) {
        User user = userMapper.checkUser(username,password);
        return user!=null;
    }

    @Override
    public boolean regist(String username, String password) {
        User user = userMapper.checkUser(username,password);
        if (user!=null) {
            return false;
        }else{
            User newUser = new User(username,password);
            Integer sign = userMapper.addUser(newUser);
            return sign==1;
        }
    }

    @Override
    public boolean delUser(Integer id) {
        Integer sign = userMapper.delUser(id);
        return sign==1;
    }

    @Override
    public boolean changeInfo(User user) {
        Integer sign = userMapper.updateUserInfo(user);
        return sign==1;
    }

    @Override
    public boolean resetPs(String username, String newPassword) {
        Integer sign = userMapper.updateUserPs(username,newPassword);
        return sign==1;
    }

    @Override
    public boolean addHistory(String uploadHistory, Integer id) {
        User user = this.findUserById(id);
        StringBuffer oldhistory = new StringBuffer(user.getUploadHistory());
        oldhistory.append(new Date().toString() + "+" +  uploadHistory);
        Integer sign = userMapper.updateHistory(oldhistory.toString(),id);
        return sign==1;
    }
}
