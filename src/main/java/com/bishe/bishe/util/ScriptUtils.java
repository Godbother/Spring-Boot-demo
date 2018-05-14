package com.bishe.bishe.util;


import com.bishe.bishe.admin.ClientConst;
import com.bishe.bishe.model.BaseResponce;
import com.bishe.bishe.model.Warc;
import com.bishe.bishe.model.esmodel.EsWarc;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ScriptUtils {
    //本类用于生成各种需要的字符串脚本

    //生成warc文件下的doc json
    public static String getWarcScript(EsWarc esWarc){
        String script = "{" +
                "    \"doc\" : {" +
                "        \"addTime\" : \""+esWarc.getAddTime()+"\"," +
                "        \"updateTime\" : \""+esWarc.getUpdateTime()+"\"," +
                "        \"warcUrl\" : \""+esWarc.getWarcUrl()+"\"," +
                "        \"responseUrl\" : \""+esWarc.getResponseUrl()+"\"," +
                "        \"create_at\" : \""+esWarc.getCreate_at()+"\"," +
                "        \"update_at\" : \""+esWarc.getUpdate_at()+"\"" +
                "        \"mimeType\" : \""+esWarc.getMimeType()+"\"" +
                "        \"headerFields\" : \""+esWarc.getHeaderFields()+"\"" +
                "        \"headerWarcType\" : \""+esWarc.getHeaderWarcType()+"\"" +
                "        \"content\" : \""+esWarc.getContent()+"\"" +
                "    }" +
                "}";
        return script;
    }

    //把返回的map转换成BaseResponce返回
    public static BaseResponce transfertoBaseResponce(Map allmap){
        List<EsWarc> warcList = (List<EsWarc>) allmap.get("warcList");
        Long total = (Long) allmap.get("total");
        BaseResponce baseResponce = new BaseResponce();
        List<Warc> warcs = new LinkedList<>();
        for (EsWarc esw :warcList) {
            Warc warc = new Warc();
            warc.setId(esw.getId());
            warc.setUpdateTime(esw.getUpdateTime());
            warc.setAddTime(esw.getAddTime());
            warc.setHeaderFields(esw.getHeaderFields().substring(0,50));
            warc.setResponseUrl(esw.getResponseUrl().substring(0,50));
            warc.setWarcUrl(esw.getWarcUrl());
            warcs.add(warc);
        }
        baseResponce.setTotal(total);
        baseResponce.setWarcList(warcs);
        return baseResponce;
//        EsWarc esWarc = new EsWarc();
//        esWarc.setAddTime((String) map.get(ClientConst.warc_field_addTime));
//        esWarc.setCreate_at((Long) map.get(ClientConst.warc_field_create_at));
//        esWarc.setUpdate_at((Long) map.get(ClientConst.warc_field_update_at));
//        esWarc.setUpdateTime((String) map.get(ClientConst.warc_field_updateTime));
//        esWarc.setResponseUrl((String) map.get(ClientConst.warc_field_responseUrl));
//        esWarc.setContent((String) map.get(ClientConst.warc_field_content));
//        esWarc.setWarcUrl((String) map.get(ClientConst.warc_field_warcUrl));
//        esWarc.setHeaderFields((String) map.get(ClientConst.warc_field_headerFields));
//        esWarc.setHeaderWarcType((String) map.get(ClientConst.warc_field_headerWarcType));
//        esWarc.setMimeType((String) map.get(ClientConst.warc_field_mimeType));
    }
}
