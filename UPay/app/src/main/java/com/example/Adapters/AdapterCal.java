package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.RecycleHolders.RecycleHolderCal;
import com.example.upay.CalData;
import com.example.upay.R;

import java.util.ArrayList;


public class AdapterCal extends RecyclerView.Adapter<RecycleHolderCal> {
    @NonNull
    @Override
    public RecycleHolderCal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowcal, parent, false);
        return new RecycleHolderCal(view);
    }
    private ArrayList<CalData> CalData;
    private Context context;

    public AdapterCal(Context context, ArrayList<CalData> CalData) {
        this.context = context;
        this.CalData = CalData;
    }
    @Override
    public void onBindViewHolder(@NonNull RecycleHolderCal holder, int position) {
        CalData K = CalData.get(position);
        holder.setDetails(K);
    }


    @Override
    public int getItemCount() {
        return CalData.size();
    }

}