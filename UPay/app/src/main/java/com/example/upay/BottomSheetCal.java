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

import com.example.Adapters.Adapter;
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

import java.text.DecimalFormat;
import java.util.ArrayList;


public class BottomSheetCal extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    public ArrayList<CalData>  calData;
    ArrayList<CalData> c;
    public AdapterCal adapter;
    private FirebaseUser user;
    //
    public ArrayList<String> names = new ArrayList<>();
    public ArrayList<String> location = new ArrayList<>();
    public ArrayList<String> amount = new ArrayList<>();
    public ArrayList<String> Date = new ArrayList<>();
    //
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

        c = new ArrayList<>();

ArrayList<String> new_date = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String x = dataSnapshot.child("User Data").child("Transaction count").getValue().toString();
                for (int i = 0; i < Integer.parseInt(x); i++) {
                    names.add(dataSnapshot.child("Transactions").child("" + i).child("Name").getValue().toString());
                    location.add(dataSnapshot.child("Transactions").child("" + i).child("Location").getValue().toString());
                    amount.add(dataSnapshot.child("Transactions").child("" + i).child("Amount").getValue().toString());
                    Date.add(dataSnapshot.child("Transactions").child("" + i).child("Date").getValue().toString());
                    //
                    String Currency = dataSnapshot.child("Currency").child("Currency").getValue().toString();

                    String r = Date.get(i);
                    String[] dateP = r.split("/");
                    String da = dateP[0];
                    String mo = dateP[1];
                    String ye = dateP[2];

                    new_date.add(String.format("%s-%s-%s", ye, mo, da));

                    Log.d("Apple1",""+LIMIT);
                    Log.d("Apple2",""+new_date.get(i));;

                    if (new_date.get(i).equals(LIMIT)) {
                        if (Currency.equals("$")) {
                            c.add(new CalData(names.get(i), location.get(i), "$" + amount.get(i), Date.get(i)));
                            recyclerView.setAdapter(new AdapterCal(getContext(), c));
                        }
                        else if (Currency.equals("€")){
                            double val = 0.83*Double.parseDouble(amount.get(i));
                            c.add(new CalData(names.get(i), location.get(i), "€" + new DecimalFormat("##.##").format(val), Date.get(i)));
                            recyclerView.setAdapter(new AdapterCal(getContext(), c));

                        }
                        else if (Currency.equals("$CA")){
                            double val = 1.23*Double.parseDouble(amount.get(i));
                            c.add(new CalData(names.get(i), location.get(i), "$CA" + new DecimalFormat("##.##").format(val), Date.get(i)));
                            recyclerView.setAdapter(new AdapterCal(getContext(), c));

                        }



                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

}
