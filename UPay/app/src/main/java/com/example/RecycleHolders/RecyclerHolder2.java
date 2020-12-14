package com.example.RecycleHolders;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upay.PersInfoData;
import com.example.upay.R;

// Recycler Holder 2 is For Personal Info Fragment;
public class RecyclerHolder2 extends RecyclerView.ViewHolder {
    private TextView Data;
    public RecyclerHolder2(View itemView) {
        super(itemView);
        Data = itemView.findViewById(R.id.txtOutput);
    }

    public void setDetails(PersInfoData items) {
        Data.setText(items.getData());

    }

}
