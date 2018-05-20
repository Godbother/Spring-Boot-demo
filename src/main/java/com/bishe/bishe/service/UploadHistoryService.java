package com.bishe.bishe.service;

import com.bishe.bishe.mapper.UploadHistoryMapper;
import com.bishe.bishe.model.UploadHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UploadHistoryService {

    public List<UploadHistory> searchAllHistory();

    public int addHistory(String filename,String username);
}
