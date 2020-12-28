package com.example.upay;

public class PurchaseItems {
    public String Name;
    public String location;
    public String amount;
//    private int imageUrl;

  public PurchaseItems (String Name, String Location, String amount) {
        this.Name = Name;
        this.location = Location;
        this.amount = amount;
//        this.imageUrl = imageUrl;
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

//    public void setImageUrl(int imageUrl) { this.imageUrl = imageUrl;}
//    public int getImageUrl() {
//        return imageUrl;
    }
