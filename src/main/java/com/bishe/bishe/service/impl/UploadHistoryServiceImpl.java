package com.bishe.bishe.service.impl;

import com.bishe.bishe.mapper.UploadHistoryMapper;
import com.bishe.bishe.model.UploadHistory;
import com.bishe.bishe.service.UploadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UploadHistoryServiceImpl implements UploadHistoryService{
    @Autowired
    private UploadHistoryMapper uploadHistoryMapper;

    @Override
    public List<UploadHistory> searchAllHistory() {
        return uploadHistoryMapper.selectAll();
    }

    @Override
    public int addHistory(String filename, String username) {
        Date date = new Date();
        String uploadtime = date.toString();
        Long uploadat = date.getTime();
        return uploadHistoryMapper.insert(filename,username,uploadtime,uploadat);
    }
}
