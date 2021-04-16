package com.example.RecycleHolders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Profile.Settings;
import com.example.upay.PersInfoData;
import com.example.upay.R;

// Recycler Holder 2 is For Personal Info Fragment;
public class RecyclerHolder2 extends RecyclerView.ViewHolder {
    private TextView Data,secondData;
    private ImageView imageView;
    private ImageButton imageButton;
    public RecyclerHolder2(View itemView) {
        super(itemView);
        Data = itemView.findViewById(R.id.txtOutput);
        secondData = itemView.findViewById(R.id.textView12);
        imageView = itemView.findViewById(R.id.imageView);
        imageButton = itemView.findViewById(R.id.theCLicker);
        //

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int position = getAdapterPosition();
                switch (position){
                    case 0:
                        Intent i = new Intent(itemView.getContext(), Settings.class);
                        i.putExtra("section","0");
                        itemView.getContext().startActivity(i);
                        break;
                    case 1:
                        Intent i2 = new Intent(itemView.getContext(), Settings.class);
                        i2.putExtra("section","1");
                        itemView.getContext().startActivity(i2);
                        break;
                    case 2:
                        Intent i3 = new Intent(itemView.getContext(), Settings.class);
                        i3.putExtra("section","2");
                        itemView.getContext().startActivity(i3);
                        break;
                    case 3:
                        Intent i4 = new Intent(itemView.getContext(), Settings.class);
                        i4.putExtra("section","3");
                        itemView.getContext().startActivity(i4);
                        break;
                }
            }
        });
    }

    public void setDetails(PersInfoData items) {
        Data.setText(items.getData());
        secondData.setText(items.getSecondData());
        Drawable drawable = imageView.getResources().getDrawable(items.getDrawable());
        imageView.setImageDrawable(drawable);
    }

}
