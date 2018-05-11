package com.bishe.bishe.service.impl;

import com.bishe.bishe.admin.ClientConst;
import com.bishe.bishe.es.EsDao;
import com.bishe.bishe.mapper.AdminMapper;
import com.bishe.bishe.model.Admin;
import com.bishe.bishe.service.AdminService;
import io.searchbox.client.JestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private EsDao esDao;

    @Override
    public boolean login(String adminname, String password) {
        Admin admin = adminMapper.checkAdmin(adminname,password);
        return admin!=null;
    }

    @Override
    public boolean delwarc(String id) {
        JestResult result = esDao.deleteDocument(ClientConst.index,ClientConst.type,id);
        System.out.println(result);
        return false;
    }
}
