package com.example.upay;

import android.graphics.drawable.Drawable;

public class PersInfoData {
    public String Data;
    public String secondData;
    public Integer drawable;

    public PersInfoData(String data, String secondData, Integer drawable) {
        Data = data;
        this.secondData = secondData;
        this.drawable = drawable;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getSecondData() {
        return secondData;
    }

    public void setSecondData(String secondData) {
        this.secondData = secondData;
    }

    public Integer getDrawable() {
        return drawable;
    }

    public void setDrawable(Integer drawable) {
        this.drawable = drawable;
    }
}
