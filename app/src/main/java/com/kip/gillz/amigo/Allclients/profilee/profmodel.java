package com.kip.gillz.amigo.Allclients.profilee;


public class profmodel {

    private int Meterno;
    private String Date_Scanned;
    private int Consumption;
    private int Amount;

    public profmodel(int Meterno, String Date_Scanned, int Consumption, int Amount) {
        this.Meterno = Meterno;
        this.Date_Scanned = Date_Scanned;
        this.Consumption = Consumption;
        this.Amount = Amount;

    }

    public String getDate_Scanned() {
        return Date_Scanned;
    }
    public int getMeterno() {
        return Meterno;
    }
    public int getConsumption() {
        return Consumption;
    }
    public int getAmount() {
        return Amount;
    }


}
