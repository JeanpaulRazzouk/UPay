package com.example.RecycleHolders;


import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upay.ForecastParam;
import com.example.upay.R;

public class ForecastHolder extends RecyclerView.ViewHolder {
    TextView textview;
    ImageView imageView;

    public ForecastHolder(@NonNull View itemView) {
        super(itemView);
        textview = itemView.findViewById(R.id.output);
        imageView = itemView.findViewById(R.id.imageView5);
    }

    public void setDetails(ForecastParam items) {
        if (items.getImage().toString().equals("2131231171")) {
            textview.setText("Spending at " + items.getShop() + " likely to increase to $" + items.getAmount());
        }
        else{
            textview.setText("Spending at "  + items.getShop() + " is likely to decrease to $" + items.getAmount());
        }
        //
        Drawable drawable = imageView.getResources().getDrawable(items.getImage());
        imageView.setImageDrawable(drawable);


    }
}
