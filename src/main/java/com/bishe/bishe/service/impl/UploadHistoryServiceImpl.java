package com.bishe.bishe.service.impl;

import com.bishe.bishe.mapper.UploadHistoryMapper;
import com.bishe.bishe.model.UploadHistory;
import com.bishe.bishe.service.UploadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String uploadtime = simpleDateFormat.format(date);
        Long uploadat = date.getTime();
        return uploadHistoryMapper.insert(filename,username,uploadtime,uploadat);
    }

    @Override
    public List<UploadHistory> searchRecent() {
        return uploadHistoryMapper.selectRecent10();
    }

    @Override
    public Map aggDayUpload() {
        Map result = new HashMap();
        List<Map> temmap = uploadHistoryMapper.dayCount();
        List<String> dateList = new LinkedList<>();
        List<Long> countList = new LinkedList<>();

        for (Map map:temmap) {
            dateList.add((String) map.get("time"));
            countList.add((Long) map.get("total"));
        }
        result.put("datelist",dateList);
        result.put("countlist",countList);

        return result;
    }
}
