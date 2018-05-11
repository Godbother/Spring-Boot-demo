package com.bishe.bishe.model;

public class Admin {
    private Integer id;
    private String adminname;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin(String adminname, String password) {
        this.adminname = adminname;
        this.password = password;
    }

    public Admin() {
    }
}
