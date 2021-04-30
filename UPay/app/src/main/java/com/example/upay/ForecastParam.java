package com.example.upay;

public class ForecastParam {
    String amount;
    String shop;
    Integer image;

    public ForecastParam(String shop,String amount, Integer image) {
        this.amount = amount;
        this.shop = shop;
        this.image = image;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
