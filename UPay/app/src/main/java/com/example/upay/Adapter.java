package com.example.upay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerHolder>{
    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new RecyclerHolder(view);
    }
    private ArrayList<PurchaseItems> items;
    private Context context;
    public Adapter(Context context, ArrayList<PurchaseItems> items) {
        this.context = context;
        this.items = items;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        PurchaseItems item = items.get(position);
        holder.setDetails(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
