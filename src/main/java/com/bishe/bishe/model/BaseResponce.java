package com.bishe.bishe.model;


import java.util.List;

public class BaseResponce {
    private List<Warc> warcList;
    private Long total;
    private Long pageacount;

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

    public Long getPageacount() {
        return pageacount;
    }

    public void setPageacount(Long pageacount) {
        this.pageacount = pageacount;
    }

    public BaseResponce(List<Warc> warcList, Long total) {
        this.warcList = warcList;
        this.total = total;
    }

    public BaseResponce() {
    }
}
