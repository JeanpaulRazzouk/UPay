package com.example.upay;

public class PersInfoData {
    public String Email;
    public String Phonenum;
    public String Passwd;

    public PersInfoData (String Email, String Phonenum, String Passwd) {
        this.Email = Email;
        this.Phonenum = Phonenum;
        this.Passwd = Passwd;
    }
    public String getEmail() {return Email; }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public String getPhonenum() {
        return Phonenum;
    }
    public void setPhonenum(String Phonenum) {
        this.Phonenum = Phonenum;
    }
    public String getPasswd() {
        return Passwd;
    }
    public void setPasswd(String Passwd) {this.Passwd = Passwd; }
}
