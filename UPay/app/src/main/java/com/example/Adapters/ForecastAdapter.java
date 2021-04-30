package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.RecycleHolders.ForecastHolder;
import com.example.RecycleHolders.RecyclerHolder;
import com.example.upay.ForecastParam;
import com.example.upay.PurchaseItems;
import com.example.upay.R;

import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastHolder>{
    @NonNull
    @Override
    public ForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_row, parent, false);
        return new ForecastHolder(view);
    }
    private ArrayList<ForecastParam> items;
    private Context context;
    public ForecastAdapter(Context context, ArrayList<ForecastParam> items) {
        this.context = context;
        this.items = items;
    }
    @Override
    public void onBindViewHolder(@NonNull ForecastHolder holder, int position) {
        ForecastParam item = items.get(position);
        holder.setDetails(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
