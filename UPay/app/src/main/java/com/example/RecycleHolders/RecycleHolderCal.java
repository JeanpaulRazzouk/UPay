package com.example.RecycleHolders;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upay.CalData;
import com.example.upay.R;

public class RecycleHolderCal extends RecyclerView.ViewHolder {

    private TextView Name,location,amount,Date;

    public RecycleHolderCal(@NonNull View itemView) {
        super(itemView);
        Name = itemView.findViewById(R.id.txtName);
        location = itemView.findViewById(R.id.txtLocation);
        amount = itemView.findViewById(R.id.txtAmount);
        Date = itemView.findViewById(R.id.textView22);
    }

    public void setDetails(CalData items) {
        Name.setText(items.getName());
        amount.setText(items.getAmount());
        location.setText(items.getLocation());
        Date.setText(items.getDate());

    }

}
