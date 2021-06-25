package com.example.Profile;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Adapters.Adapter2;
import com.example.External.LoginPage;
import com.example.upay.PersInfoData;
import com.example.upay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PersonalInfoFragment  extends BottomSheetDialogFragment {
    public ArrayList<PersInfoData> PersArray;
    public RecyclerView recyclerView;
    public Adapter2 adapter;
    private DatabaseReference mDatabase;
    //
    ImageButton imageButton;
    private FirebaseUser user;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_personal_info_frag, container, false);
//
        recyclerView = view.findViewById(R.id.recycle2);
        imageButton = view.findViewById(R.id.dlt_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PersArray = new ArrayList<>();

        adapter = new Adapter2(getContext(), PersArray);
        recyclerView.setAdapter(adapter);
        //
        Activity();
        //
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           showForgotDialog();
            }
        });
        return  view;
    }


    public void delete_account(String password){
        // TODO() UNDER CONSTRUCTION;
        Toast.makeText(getContext(),"This option is currently not available",Toast.LENGTH_SHORT).show();
    }

    private void showForgotDialog() {
        final EditText taskEditText = new EditText(getContext());
        AlertDialog dialog = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogStyle)
                .setTitle("Password Verification")
                .setMessage("Please enter your password")
                .setView(taskEditText)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String task = String.valueOf(taskEditText.getText());
                            delete_account(task);
                        }catch(Exception e){}
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.getWindow().setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.dialog_bg));
        dialog.show();
    }



    public void Activity (){
        user = FirebaseAuth.getInstance().getCurrentUser();
        //
        String t [] = new String[4];
        String t2 [] = new String[4];
        Integer t3 [] = new Integer[4];

        t[0] = "Change Info";
        t[1] = "Currency Settings";
        t[2] = "Supported Cards";
        t[3] = "Other Options";

        t2[0] = "Change Personal Preference";
        t2[1] = "Change Currency type";
        t2[2] = "Check Which Card are supported";
        t2[3] = "NFC and Security Parameters";

        t3[0] = R.drawable.ic_user;
        t3[1] =R.drawable.ic_currency;
        t3[2] =R.drawable.ic_credit_card__2_;
        t3[3] =R.drawable.ic_more;

        //
        for (int i = 0; i<=3 ;i++) {
            PersInfoData p = new PersInfoData(t[i],t2[i],t3[i]);
            PersArray.add(p);
        }
    }

}


