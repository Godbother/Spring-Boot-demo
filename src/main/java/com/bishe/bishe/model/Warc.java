package com.bishe.bishe.model;

public class Warc {
    private String id;
    private String addTime;
    private String updateTime;
    private String warcUrl;
    private String responseUrl;
    private String headerFields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getWarcUrl() {
        return warcUrl;
    }

    public void setWarcUrl(String warcUrl) {
        this.warcUrl = warcUrl;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    public String getHeaderFields() {
        return headerFields;
    }

    public void setHeaderFields(String headerFields) {
        this.headerFields = headerFields;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
