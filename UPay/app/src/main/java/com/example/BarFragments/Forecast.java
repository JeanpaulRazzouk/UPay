package com.example.BarFragments;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.Profile.Profile;
import com.example.payment.BottomSheetNFC;
import com.example.upay.R;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import androidx.annotation.NonNull;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;




public class Forecast extends AppCompatActivity {

    private FirebaseUser user;
    ImageButton imageButton;
    ArrayList<String> Date;
    ArrayList<String> amount;
    ArrayList<String> Name;
    ValueLineChart mCubicValueLineChart;
    PieChart mPieChart;
    TextView textView;
    TextView textView2;
    TextView textView3;
    private DatabaseReference mDatabase;
    public Forecast() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.activity_forecast);
        if (Build.VERSION.SDK_INT >= 21) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        mCubicValueLineChart = (ValueLineChart) findViewById(R.id.cubiclinechart);
        mPieChart = findViewById(R.id.piechart);
        textView = findViewById(R.id.textView20);
        textView2 = findViewById(R.id.textView27);
        textView3 = findViewById(R.id.textView28);
        imageButton = findViewById(R.id.imageButton11);
       // methods;
       LRegression();
       //
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CutBottomSheet bottomSheet = new CutBottomSheet();
                bottomSheet.show(getSupportFragmentManager(),"TAG");
            }
        });
    }
    public void LRegression(){
        Date = new ArrayList<>();
        amount = new ArrayList<>();
        Name  = new ArrayList<>();
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
                    Date d = null;
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
                series.setColor(0xFF2196F3);
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


                for (int i = 0; i<=11;i++){
                    try {
                        if (AmountY.get(i) != 0.0f) {
                            series.addPoint(new ValueLinePoint("" + i, AmountY.get(i)));
                        }
                    }catch(Exception e){
                        if (predictForValue(i, DateX, AmountY) >=0 ) {
                            series.addPoint(new ValueLinePoint("" + i, Float.parseFloat("" + predictForValue(i, DateX, AmountY))));
                        }else{}
                    }
                }


                series.addPoint(new ValueLinePoint("", 0));
                mCubicValueLineChart.addSeries(series);
                mCubicValueLineChart.startAnimation();
                // CumData
                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month = localDate.getMonthValue();
                int h = month;
                //
                textView.setText("$" + (predictForValue(h, DateX, AmountY)));
                // present to next month increase decrease;
                float this_month = Float.parseFloat(dataSnapshot.child("User Data").child("This Month").getValue().toString());

                if (this_month < predictForValue(h, DateX, AmountY)) {
                    float final_value =  Float.parseFloat(""+predictForValue(h, DateX, AmountY)) - this_month;
                    textView2.setText("Spending is Likely \n to Increase \n by "+"+"+ "$"+final_value);
                }
                else if (this_month > predictForValue(h, DateX, AmountY)){
                    float final_value = this_month - Float.parseFloat(""+predictForValue(h, DateX, AmountY));
                    textView2.setText("Spending is Likely \n to Decrease \n by "+"$"+ final_value);
                }
                // Data to Pie Chart (This month)

                mDatabase = FirebaseDatabase.getInstance().getReference();
                //
                for (int j = 0; j < Integer.parseInt(x); j++) {
                    mPieChart.addPieSlice(new PieModel(Name.get(j), Float.parseFloat(amount.get(j)), Color.parseColor(getColor())));
                }

                mPieChart.startAnimation();
                //
                    // cut % algorithm;
                    String income = dataSnapshot.child("User Data").child("income").getValue().toString();

                        float percent_value = 0.0f;
                        float val = 0;
                        for (int j = 0; j< AmountY.size();j++) {
                            if (AmountY.get(j) != 0.0f) {
                                val = val + AmountY.get(j);
                                percent_value = (val / Float.parseFloat(income)) - (Float.parseFloat("" + predictForValue(month, DateX, AmountY)/Float.parseFloat(income)));
                            }
                        }
                        textView3.setText(" Recommended future \n spending to be cut \n by " + Math.round(percent_value*100) +"%");
                for (int j = 0; j< AmountY.size();j++) {
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    HashMap<String, Object> values = new HashMap<>();
                    values.put("percent value", percent_value);
                    mDatabase.child("Users").child(user.getUid()).child("User Data").updateChildren(values);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Double predictForValue(int predictForDependentVariable, ArrayList<Integer> xx, ArrayList<Float> y) {
        if (xx.size() != y.size())
            throw new IllegalStateException("Must have equal X and Y data points");

        Integer numberOfDataValues = xx.size();

        List<Double> xSquared = xx
                .stream()
                .map(position -> Math.pow(position, 2))
                .collect(Collectors.toList());

        List<Integer> xMultipliedByY = IntStream.range(0, numberOfDataValues)
                .map(i -> (int) (xx.get(i) * y.get(i)))
                .boxed()
                .collect(Collectors.toList());

        Integer xSummed = xx
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Float ySummed = y
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double sumOfXSquared = xSquared
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Integer sumOfXMultipliedByY = xMultipliedByY
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        int slopeNominator = (int) (numberOfDataValues * sumOfXMultipliedByY - ySummed * xSummed);
        Double slopeDenominator = numberOfDataValues * sumOfXSquared - Math.pow(xSummed, 2);
        Double slope = slopeNominator / slopeDenominator;

        double interceptNominator = ySummed - slope * xSummed;
        double interceptDenominator = numberOfDataValues;
        Double intercept = interceptNominator / interceptDenominator;

        return Double.valueOf(Math.round((slope * predictForDependentVariable) + intercept));
    }

    public void cut(View view){





    }

    public String getColor() {
        // Red
        String red;
        // Green
        String green;
        // blue
        String blue;
        // generate random object
        Random random = new Random();
        // generate a red color code
        red = Integer.toHexString(random.nextInt(256)).toUpperCase();
        // generate green color code
        green = Integer.toHexString(random.nextInt(256)).toUpperCase();
        // color code generating blue
        blue = Integer.toHexString(random.nextInt(256)).toUpperCase();

        // determine the number of bits Code Red
        red = red.length() == 1 ? "0" + red : red;
        // determine the number of bits of green code
        green = green.length() == 1 ? "0" + green : green;
        // code to determine the number of bits of blue
        blue = blue.length() == 1 ? "0" + blue : blue;
        // generate hexadecimal color values
        String color = "#" + red + green + blue;
        return color;
    }

}