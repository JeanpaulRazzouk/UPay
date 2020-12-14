package com.example.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Adapters.Adapter2;
import com.example.upay.BottomSheetPayPal;
import com.example.upay.PersInfoData;
import com.example.upay.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

public class PersonalInfoFragment  extends BottomSheetDialogFragment {
    public ArrayList<PersInfoData> PersArray;
    public RecyclerView recyclerView;
    public Adapter2 adapter;
    //
    private FirebaseUser user;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_personal_info_frag, container, false);
//
        recyclerView = view.findViewById(R.id.recycle2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PersArray = new ArrayList<>();
        adapter = new Adapter2(getContext(), PersArray);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        //
        Activity();
        return  view;
    }


    public void Activity (){
        user = FirebaseAuth.getInstance().getCurrentUser();
        //
        String t [] = new String[4];
        t[0] = user.getEmail();
        t[1] = "Password (N/S)";
        t[2] = "N/A"+user.getPhoneNumber();
        t[3] = "N/A";

        //
        for (int i = 0; i<=3 ;i++) {
            PersInfoData p = new PersInfoData(t[i]);
            PersArray.add(p);
        }
    }

}


