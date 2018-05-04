package com.bishe.bishe.service;

import com.bishe.bishe.model.User;

public interface UserService {
    public User findUserById(Integer id);

    public boolean login(String username,String password);
}
