package com.kip.gillz.amigo.Agentsss;


public class agentsmodel {

    private String Username;
    private String mobile;
    private String status;
    private String reg_date;


    public agentsmodel(String Username, String mobile, String status, String reg_date) {
        this.Username = Username;
        this.mobile = mobile;
        this.status = status;
        this.reg_date = reg_date;


    }

    public String getUsername() {
        return Username;
    }
    public String getMobile() {
        return mobile;
    }
    public String getStatus() {
        return status;
    }
    public String getReg_date() {
        return reg_date;
    }


}
