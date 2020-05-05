package com.kip.gillz.amigo.Myread;


public class Myreadmodel {


    private String Date_Scanned;
    private int Scans;


    public Myreadmodel(String Date_Scanned, int Scans) {
        this.Date_Scanned = Date_Scanned;
        this.Scans = Scans;
    }

    public String getDate_Scanned() {
        return Date_Scanned;
    }

    public int getScans
            () {
        return Scans;
    }


}
