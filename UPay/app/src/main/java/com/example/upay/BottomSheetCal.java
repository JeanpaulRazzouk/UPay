package com.example.upay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Adapters.AdapterCal;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;


public class BottomSheetCal extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    public ArrayList<CalData>  calData;
//    CalData [] c;
    ArrayList<CalData> c;
    public AdapterCal adapter;
    private FirebaseUser user;
    //
    public ArrayList<String> names = new ArrayList<>();
    public ArrayList<String> location = new ArrayList<>();
    public ArrayList<String> amount = new ArrayList<>();
    public ArrayList<String> Date = new ArrayList<>();
    //
    SharedPreferences sharedPreferences;
    public String x; // number of transactions;
    public String TransCount;
    public static final String SOME_INTENT_FILTER_NAME = "SOME_INTENT_FILTER_NAME";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view1 = inflater.inflate(R.layout.activity_bottom_sheet_cal, container, false);
        recyclerView = view1.findViewById(R.id.recyclerView);

        calData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        Activity();

        return view1;
    }


    public void Activity(){
        user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("User Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String x = dataSnapshot.child("Transaction count").getValue().toString();
                    //
                    sharedPreferences = getContext().getSharedPreferences(TransCount, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(TransCount, x);
                    editor.apply();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sharedPreferences = getContext().getSharedPreferences(TransCount, Context.MODE_PRIVATE);
        x = sharedPreferences.getString(TransCount, null);


        c = new ArrayList<>();
        String [] t1 = new String[Integer.parseInt(x)]; // names;

        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Transactions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (int i = 0; i < Integer.parseInt(x); i++) {
                    names.add(dataSnapshot.child("" + i).child("Name").getValue().toString());
                    location.add(dataSnapshot.child("" + i).child("Location").getValue().toString());
                    amount.add(dataSnapshot.child("" + i).child("Amount").getValue().toString());
                    Date.add(dataSnapshot.child("" + i).child("Date").getValue().toString());

//                    if (Date.get(i).equals(value)) {
                        c.add(new CalData(names.get(i), location.get(i), "$" + amount.get(i), Date.get(i)));
                        recyclerView.setAdapter(new AdapterCal(getContext(), c));
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

}
