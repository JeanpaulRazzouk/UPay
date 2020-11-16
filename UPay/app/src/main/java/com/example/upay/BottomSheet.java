package com.example.upay;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheet extends BottomSheetDialogFragment {
    ImageButton imageButton;
    EditText editText,editText2,editText3,editText4;
    //
    String card_number,date,CVV,Phone_Number;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view1 = inflater.inflate(R.layout.activity_bottom_sheet, container, false);
        //
        imageButton = view1.findViewById(R.id.imageButton9);
        //
        editText = view1.findViewById(R.id.editText7);
        editText2 = view1.findViewById(R.id.editText9);
        editText3 = view1.findViewById(R.id.editText10);
        editText4 = view1.findViewById(R.id.editText12);
        //
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                card_number = (editText.getText().toString());
                date = editText2.getText().toString();
                CVV = (editText3.getText().toString());
                Phone_Number = (editText4.getText().toString());

                if (editText.getText().toString().isEmpty() == true && editText2.getText().toString().isEmpty()== true
                        && editText3.getText().toString().isEmpty()== true && editText4.getText().toString().isEmpty()==true) {
                    Toast.makeText(view1.getContext()," Please fill empty fields ",Toast.LENGTH_SHORT).show();
                }
                else if (editText.getText().toString().isEmpty() == false && editText2.getText().toString().isEmpty()== false
                        && editText3.getText().toString().isEmpty()== false && editText4.getText().toString().isEmpty()==false){
                    //
                    dismiss();
                    //
                    Intent i = new Intent(view1.getContext(), HomePage.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(view1.getContext()," Please fill empty fields ",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view1;
    }
}
