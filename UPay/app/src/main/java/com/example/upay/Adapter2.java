package com.example.upay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter2 extends RecyclerView.Adapter<RecyclerHolder2> {
    @NonNull
    @Override
    public RecyclerHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row2, parent, false);
        return new RecyclerHolder2(view);
    }

    private ArrayList<PersInfoData> persInfoData;
    private Context context;
    public Adapter2(Context context, ArrayList<PersInfoData> persInfoData) {
        this.context = context;
        this.persInfoData = persInfoData;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder2 holder, int position) {
        PersInfoData persInfoDatas = persInfoData.get(position);
        holder.setDetails(persInfoDatas);
    }

    @Override
    public int getItemCount() {
        return persInfoData.size();
    }

}
