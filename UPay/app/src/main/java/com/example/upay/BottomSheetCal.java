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
    SharedPreferences sharedPreferences2;
    public String x; // number of transactions;
    public String TransCount;
    String LIMIT;
    public BottomSheetCal(String fin) {
        LIMIT = fin;
    }

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

ArrayList<String> new_date = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Transactions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (int i = 0; i < Integer.parseInt(x); i++) {
                    names.add(dataSnapshot.child("" + i).child("Name").getValue().toString());
                    location.add(dataSnapshot.child("" + i).child("Location").getValue().toString());
                    amount.add(dataSnapshot.child("" + i).child("Amount").getValue().toString());
                    Date.add(dataSnapshot.child("" + i).child("Date").getValue().toString());


                    String r = Date.get(i);
                    String[] dateP = r.split("/");
                    String da = dateP[0];
                    String mo = dateP[1];
                    String ye = dateP[2];

                    new_date.add(String.format("%s-%s-%s", ye, mo, da));

                    Log.d("Apple1",""+LIMIT);
                    Log.d("Apple2",""+new_date.get(i));;

                    if (new_date.get(i).equals(LIMIT)) {
                        c.add(new CalData(names.get(i), location.get(i), "$" + amount.get(i), Date.get(i)));
                        recyclerView.setAdapter(new AdapterCal(getContext(), c));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

}
