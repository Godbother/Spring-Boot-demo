package com.bishe.bishe.model;

import java.util.List;

public class DetailResponce {
    private Integer code;
    private String msg;
    private WarcDetail warcDetailList;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public WarcDetail getWarcDetailList() {
        return warcDetailList;
    }

    public void setWarcDetailList(WarcDetail warcDetailList) {
        this.warcDetailList = warcDetailList;
    }
}
