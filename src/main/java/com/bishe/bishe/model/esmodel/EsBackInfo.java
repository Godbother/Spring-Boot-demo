package com.bishe.bishe.model.esmodel;

import java.util.Map;

public class EsBackInfo {
    private String index;
    private String type;
    private String id;
    private Double score;
    private Map<String,Object> source;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Map<String,Object> getSource() {
        return source;
    }

    public void setSource(Map<String,Object> source) {
        this.source = source;
    }
}
