package com.bishe.bishe.service;

public interface AdminService {
    /**
     * 管理员登录方法
     */
    public boolean login(String adminname,String password);

    /**
     * 删除warc文件，维护用
     */
    public boolean delwarc(String id);
}
