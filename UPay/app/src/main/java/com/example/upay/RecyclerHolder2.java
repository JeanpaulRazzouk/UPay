package com.example.upay;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerHolder2 extends RecyclerView.ViewHolder {
    private TextView Data;
    private CardView cardView;
    Animation animation;

    public RecyclerHolder2(View itemView) {
        super(itemView);
        Data = itemView.findViewById(R.id.txtOutput);
        //
        cardView = itemView.findViewById(R.id.income);
        animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.open_animation);
        animation.setDuration(1200);
        cardView.setAnimation(animation);
    }

    public void setDetails(PersInfoData items) {
        Data.setText(items.getEmail());

    }

}
