package com.example.RecycleHolders;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payment.BottomSheetNFC;
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
                        //TODO()
                        break;
                    case 1:
                        //TODO()
                        break;
                    case 2:
                        //TODO()
                        break;
                    case 3:
                        //TODO()
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
