package com.bishe.bishe.service;

import com.bishe.bishe.mapper.UploadHistoryMapper;
import com.bishe.bishe.model.UploadHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface UploadHistoryService {

    public List<UploadHistory> searchAllHistory();

    public int addHistory(String filename,String username);

    public List<UploadHistory> searchRecent();

    public Map aggDayUpload();
}
