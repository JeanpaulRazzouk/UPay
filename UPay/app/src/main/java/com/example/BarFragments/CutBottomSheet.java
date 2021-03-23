package com.example.BarFragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.upay.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class CutBottomSheet extends BottomSheetDialogFragment {

    ValueLineChart mCubicValueLineChart;
    private FirebaseUser user;
    ArrayList<String> Date = new ArrayList<>();
    ArrayList<String> amount = new ArrayList<>();
    ArrayList<String> Name  = new ArrayList<>();

    public CutBottomSheet() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view1 = inflater.inflate(R.layout.cut_bottom_sheet, container, false);
        mCubicValueLineChart = view1.findViewById(R.id.cubiclinechartop);
        //
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String x = dataSnapshot.child("User Data").child("Transaction count").getValue().toString();
                for (int i = 0; i < Integer.parseInt(x); i++) {
                    Name.add(dataSnapshot.child("Transactions").child("" + i).child("Name").getValue().toString());
                    amount.add(dataSnapshot.child("Transactions").child("" + i).child("Amount").getValue().toString());
                    Date.add(dataSnapshot.child("Transactions").child("" + i).child("Date").getValue().toString());
                }


                Float m1 = 0.0f;
                Float m2 = 0.0f;
                Float m3 = 0.0f;
                Float m4 = 0.0f;
                Float m5 = 0.0f;
                Float m6 = 0.0f;
                Float m7 = 0.0f;
                Float m8 = 0.0f;
                Float m9 = 0.0f;
                Float m10 = 0.0f;
                Float m11 = 0.0f;
                Float m12 = 0.0f;
                //int month_num;
                for (int i = 0; i < Integer.parseInt(x); i++) {
                    String date = Date.get(i);
                    String[] dateParts = date.split("/");
                    String day = dateParts[0];
                    String month = dateParts[1];
                    String year = dateParts[2];

                    // First convert to Date. This is one of the many ways.
                    String dateString = String.format("%s-%s-%s", year, month, day);
                    java.util.Date d = null;
                    try {
                        d = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // Then get the month of the year from the Date based on specific locale.
                    String month_of_year = new SimpleDateFormat("M", Locale.ENGLISH).format(d);

                    try {
                        switch (month_of_year) {

                            case "1":
                                m1 = m1 + Float.parseFloat(amount.get(i));
                                break;
                            case "2":
                                m2 = m2 + Float.parseFloat(amount.get(i));
                                break;
                            case "3":
                                m3 = m3 + Float.parseFloat(amount.get(i));
                                break;
                            case "4":
                                m4 = m4 + Float.parseFloat(amount.get(i));
                                break;
                            case "5":
                                m5 = m5 + Float.parseFloat(amount.get(i));
                                break;
                            case "6":
                                m6 = m6 + Float.parseFloat(amount.get(i));
                                break;
                            case "7":
                                m7 = m7 + Float.parseFloat(amount.get(i));
                                break;
                            case "8":
                                m8 = m8 + Float.parseFloat(amount.get(i));
                                break;
                            case "9":
                                m9 = m9 + Float.parseFloat(amount.get(i));
                                break;
                            case "10":
                                m10 = m10 + Float.parseFloat(amount.get(i));
                                break;
                            case "11":
                                m11 = m11 + Float.parseFloat(amount.get(i));
                                break;
                            case "12":
                                m12 = m12 + Float.parseFloat(amount.get(i));
                                break;
                        }
                    } catch(Exception e ){}
                }


                // setting data in first graph;
                ValueLineSeries series = new ValueLineSeries();
                series.setColor(0xFFe71837);
                series.addPoint(new ValueLinePoint("", 0));

                ArrayList<Integer> DateX = new ArrayList<>(Arrays. asList(1,2,3,4,5,6,7,8,9,10,11,12));
                ArrayList<Float> AmountY = new ArrayList<>();
                AmountY.add(m1);
                AmountY.add(m2);
                AmountY.add(m3);
                AmountY.add(m4);
                AmountY.add(m5);
                AmountY.add(m6);
                AmountY.add(m7);
                AmountY.add(m8);
                AmountY.add(m9);
                AmountY.add(m10);
                AmountY.add(m11);
                AmountY.add(m12);
                //
                AmountY.removeAll(Collections.singleton(0.0f));
                //

                for (int i = 0; i<12;i++){
                    try {
                        if (AmountY.get(i) != 0.0f) {
                        }
                    }catch(Exception e){
                        DateX.set(i,0);
                    }
                }

                DateX.removeAll(Collections.singleton(0));
                // continuity of the algorithm;
                    try {
                        float percent_value = Float.parseFloat(dataSnapshot.child("User Data").child("percent value").getValue().toString());
                        Forecast forecast = new Forecast();
                        for (int j = 0; j<=11;j++) {
                                if (forecast.predictForValue(j, DateX, AmountY) >= 0) {
                                    series.addPoint(new ValueLinePoint("" + j, Float.parseFloat("" + percent_value * (forecast.predictForValue(j, DateX, AmountY)))));
                                }
                        }
                    }catch(Exception e){

                    }


                series.addPoint(new ValueLinePoint("", 0));
                mCubicValueLineChart.addSeries(series);
                mCubicValueLineChart.startAnimation();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        return view1;

    }
}
