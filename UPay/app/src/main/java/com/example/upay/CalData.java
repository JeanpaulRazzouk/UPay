package com.example.upay;


import java.util.Set;

public class CalData {

    public String Name;
    public String location;
    public String amount;
    public  String Date;

    public CalData (String Name, String Location, String amount,String Date) {
        this.Name = Name;
        this.location = Location;
        this.amount = amount;
        this.Date = Date;
    }
    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return Date;
    }
    public void setDate(String Date) {
        this.Date = Date;
    }
}
