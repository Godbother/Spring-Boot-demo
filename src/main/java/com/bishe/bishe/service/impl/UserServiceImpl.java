package com.bishe.bishe.service.impl;

import com.bishe.bishe.mapper.UserMapper;
import com.bishe.bishe.model.User;
import com.bishe.bishe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        User user = userMapper.checkUser(username, password);
        if (user!=null) {
            return true;
        }else {
            return false;
        }
    }
}
