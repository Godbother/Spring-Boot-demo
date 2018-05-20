package com.bishe.bishe.util;


import com.bishe.bishe.admin.ClientConst;
import com.bishe.bishe.model.BaseResponce;
import com.bishe.bishe.model.DetailResponce;
import com.bishe.bishe.model.Warc;
import com.bishe.bishe.model.WarcDetail;
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

    //把返回的map转换成DetailResponce返回
    public static DetailResponce transfertoDetailResponce(Map map) throws Exception{
        String msg = new String ();
        DetailResponce detailResponce = new DetailResponce();

        WarcDetail warcDetail = new WarcDetail();
        warcDetail.setId((String) map.get("id"));
        warcDetail.setUpdateTime((String) map.get("updateTime"));
        warcDetail.setAddTime((String) map.get("addTime"));
        String headerfields = (String) map.get("headerFields");
        String content = (String) map.get("content");
        String responceurl = (String) map.get("responseUrl");
        if (headerfields.length()<1000){
            warcDetail.setHeaderFields(headerfields.substring(0,headerfields.length()));
        }else {
            warcDetail.setHeaderFields(headerfields.substring(0,1000));
        }
        if (content.length()<1000) {
            warcDetail.setContent(content.substring(0,headerfields.length()));
        }else {
            warcDetail.setContent(content.substring(0,1000));
        }
        if (responceurl.length()<1000) {
            warcDetail.setResponseUrl(responceurl.substring(0,headerfields.length()));
        }else {
            warcDetail.setResponseUrl(responceurl.substring(0,1000));
        }
        warcDetail.setWarcUrl((String) map.get("warcUrl"));
        detailResponce.setWarcDetailList(warcDetail);
        return detailResponce;
    }

    public static String datailresponceToHtml(DetailResponce detailResponce,String keyword,String id){
        WarcDetail warcDetail = detailResponce.getWarcDetailList();
        StringBuffer temstr = new StringBuffer("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>warc文件详情页</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div><ul>");
        temstr.append("<li>Id:" + id + "</li>");
        temstr.append("<li>入库时间:" + warcDetail.getAddTime() + "</li>");
        temstr.append("<li>更新时间:" + warcDetail.getUpdateTime() + "</li>");
        temstr.append("<li>生成URL:" + warcDetail.getWarcUrl() + "</li>");
        temstr.append("<li>header域:" + warcDetail.getHeaderFields() + "</li>");
        temstr.append("<li>响应URL:" + warcDetail.getResponseUrl() + "</li>");
        temstr.append("<li>内容:" + warcDetail.getContent() + "</li>");
        temstr.append("</ul></div></body>\n" +
                "</html>");
        String newString = "<b style='color:red'>" + keyword +  "</b>";
        String result = null;
        if (keyword!=null) {
            result = temstr.toString().replaceAll(keyword,newString);
        }
        return result;
    }
}
