package com.bishe.bishe.service;

import com.bishe.bishe.model.User;

public interface UserService {
    public User findUserById(Integer id);

    public boolean login(String username,String password);

    public boolean regist(String username,String password);

    public boolean delUser(Integer id);

    public boolean changeInfo(User user);

    public boolean resetPs(String username,String newPassword);

    public boolean addHistory(String uploadHistory,Integer id);
}
