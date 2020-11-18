package com.example.upay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class AnalyticsFrag extends Fragment {
TextView textView;
Animation animation;

    public AnalyticsFrag() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);
        textView = view.findViewById(R.id.textView3);
        //
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.open_animation);
        animation.setDuration(1200);
        textView.startAnimation(animation);
        return view;
    }
}