package com.bishe.bishe.model;


import java.util.List;

public class BaseResponce {
    private List<Warc> warcList;
    private Long total;

    public List<Warc> getWarcList() {
        return warcList;
    }

    public void setWarcList(List<Warc> warcList) {
        this.warcList = warcList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public BaseResponce(List<Warc> warcList, Long total) {
        this.warcList = warcList;
        this.total = total;
    }

    public BaseResponce() {
    }
}
