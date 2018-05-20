package com.bishe.bishe.controller;

import com.bishe.bishe.admin.ClientConst;
import com.bishe.bishe.es.EsDao;
import com.bishe.bishe.model.BaseResponce;
import com.bishe.bishe.model.DetailResponce;
import com.bishe.bishe.util.ScriptUtils;
import io.searchbox.client.JestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    @RequestMapping(value = "/{size}/{page}/{keyword}/kwsearch",method = RequestMethod.POST, produces="application/json")
    public BaseResponce keywordSearch(HttpSession session,
                                @PathVariable("size") Integer size,
                                @PathVariable("page") Integer page,
                                @PathVariable("keyword") String keyword){
        Map result = esDao.createSearch(keyword, ClientConst.index,size,page);
        BaseResponce baseResponce = ScriptUtils.transfertoBaseResponce(result);
        return baseResponce;
    }

    //本方法只给出一周内，一月内，一年内信息
    @RequestMapping(value = "/{size}/{page}/{distance}/{to}/{field}/timesearch",method = RequestMethod.POST, produces="application/json")
    public BaseResponce timesearch(HttpSession session,
                             @PathVariable("distance") String distance,
                             @PathVariable("to") Long to, @PathVariable("size") Integer size, @PathVariable("page") Integer page,
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
        Map result = esDao.createSearch(ClientConst.index,field,from,to,size,page);
        BaseResponce baseResponce = ScriptUtils.transfertoBaseResponce(result);
        return baseResponce;
    }


    @RequestMapping(value = "/{size}/{page}/{field}/{keyword}/fieldsearch",method = RequestMethod.POST, produces="application/json")
    public BaseResponce fieldsearch(HttpSession session,
                             @PathVariable("keyword") String keyword,
                                    @PathVariable("size") Integer size,
                                    @PathVariable("page") Integer page,
                             @PathVariable("field") String field){
        Map result = esDao.createSearch(keyword,ClientConst.index,field,size,page);
        BaseResponce baseResponce = ScriptUtils.transfertoBaseResponce(result);
        return baseResponce;
    }

    @RequestMapping(value = "/{id}/{keyword}/idsearch",method = RequestMethod.POST)
    public String fieldsearch(HttpSession session,
                           HttpServletRequest request,
                           @PathVariable("keyword") String keyword,
                           @PathVariable("id") String id){
        Map result = esDao.getDocument(ClientConst.index,ClientConst.type,id);
        DetailResponce detailResponce = null;
        try{
            detailResponce = ScriptUtils.transfertoDetailResponce(result);
        }catch (Exception e){
            detailResponce.setCode(0);
            detailResponce.setMsg("出错，错误信息如下/n" + e.getMessage());
        }
        return ScriptUtils.datailresponceToHtml(detailResponce,keyword,id);
    }

    @RequestMapping(value = "/{size}/{page}/searchall", produces="application/json")
    public BaseResponce searchAll(@PathVariable("size") Integer size,
                         @PathVariable("page") Integer page){
        Map result = esDao.searchAll(ClientConst.index,size,page);
        BaseResponce baseResponce = ScriptUtils.transfertoBaseResponce(result);
        baseResponce.setPageacount(baseResponce.getTotal()/10+1);
        return baseResponce;
    }

    @RequestMapping(value = "/delwarcbyid", produces="application/json")
    public ModelAndView delwarcbyid(@RequestParam("id") String id){
        Boolean isSuccess = esDao.deleteDocument(ClientConst.index,ClientConst.type,id);
        ModelAndView modelAndView = new ModelAndView();
        if (isSuccess) {
            modelAndView.addObject("msg","删除成功");
            modelAndView.setViewName("back_warccharge.html");
            return modelAndView;
        }else {
            modelAndView.addObject("msg","删除失败，未知错误");
            modelAndView.setViewName("back_warccharge.html");
            return modelAndView;
        }
    }
}
