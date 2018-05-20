package com.bishe.bishe.model;

public class UploadHistory {
    private Integer id;
    private String filename;
    private String username;
    private String uploadtime;
    private Long uploadat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public Long getUploadat() {
        return uploadat;
    }

    public void setUploadat(Long uploadat) {
        this.uploadat = uploadat;
    }
}
