package com.bishe.bishe.controller;

import com.bishe.bishe.admin.ClientConst;
import com.bishe.bishe.es.EsDao;
import com.bishe.bishe.model.esmodel.EsWarc;
import com.bishe.bishe.util.ScriptUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class EsSearchController {

    @Autowired
    private EsDao esDao;

    /**
     * 搜索关键字
     * @param session
     * @param keyword
     * @return
     */
    @RequestMapping(value = "/{keyword}/kwsearch",method = RequestMethod.POST)
    public String keywordSearch(HttpSession session,
                                @PathVariable("keyword") String keyword){
        Map result = esDao.createSearch(keyword, ClientConst.index,ClientConst.type);
        List<EsWarc> warcList = ScriptUtils.transfertoEswarc(result);
        Gson gson = new Gson();
        String warcjson = gson.toJson(warcList);
        return warcjson;
    }

    //本方法只给出一周内，一月内，一年内信息
    @RequestMapping(value = "/{distance}/{to}/{field}/timesearch",method = RequestMethod.POST)
    public String timesearch(HttpSession session,
                             @PathVariable("distance") String distance,
                             @PathVariable("to") Long to,
                             @PathVariable("field") String field){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(to));
        if (distance.equals(ClientConst.time_distance_week)) {
            calendar.add(Calendar.WEEK_OF_YEAR,-1);
        } else if (distance.equals(ClientConst.time_distance_month)) {
            calendar.add(Calendar.MONTH,-1);
        } else if (distance.equals(ClientConst.time_distance_year)) {
            calendar.add(Calendar.YEAR,-1);
        }
        Long from = calendar.getTime().getTime();
        Map result = esDao.createSearch(ClientConst.index,field,from,to);
        List<EsWarc> warcList = ScriptUtils.transfertoEswarc(result);
        Gson gson = new Gson();
        String warcjson = gson.toJson(warcList);
        return warcjson;
    }


    @RequestMapping(value = "/{field}/{keyword}/fieldsearch",method = RequestMethod.POST)
    public String fieldsearch(HttpSession session,
                             @PathVariable("keyword") String keyword,
                             @PathVariable("field") String field){
        Map result = esDao.createSearch(keyword,ClientConst.index,field);
        List<EsWarc> warcList = ScriptUtils.transfertoEswarc(result);
        Gson gson = new Gson();
        String warcjson = gson.toJson(warcList);
        return warcjson;
    }
}
