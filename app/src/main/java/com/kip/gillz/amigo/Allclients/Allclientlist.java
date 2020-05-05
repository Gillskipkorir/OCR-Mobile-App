package com.kip.gillz.amigo.Allclients;


public class Allclientlist {

    private String fname;
    private String lname;
    private String oname;
    private String gender;
    private String email;
    private int phone;
    private int idno;
    private int meterno;
    private String krapin;
    private String adress;
    private String postal;
    private String reg_date;


    public Allclientlist(String fname, String lname,String oname,String gender,String email, int phone,int idno ,int meterno,String krapin,String adress,String postal,String reg_date) {
        this.fname = fname;
        this.lname = lname;
        this.oname = oname;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.idno = idno;
        this.meterno = meterno;
        this.krapin = krapin;
        this.adress = adress;
        this.postal = postal;
        this.reg_date = reg_date;


    }

    public String getFname() {
        return fname;
    }
    public String getLname() {
        return lname;
    }
    public String getOname() {
        return oname;
    }public String getGender() {
        return gender;
    }public String getKrapin
            () {
        return krapin;
    }public String getAdress() {
        return adress;
    }public String getPostal() {
        return postal;
    }public String getReg_date() {
        return reg_date;
    }
    public int getMeterno() {
        return meterno;
    }
    public String getEmail() {
        return email;
    }
    public int getPhone() {
        return phone;
    }
    public int getIdno
            () {
        return idno;
    }


}
