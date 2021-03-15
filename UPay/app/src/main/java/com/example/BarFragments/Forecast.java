package com.example.BarFragments;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import com.example.upay.R;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;




public class Forecast extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseUser user;
    ArrayList<String> Date;
    ArrayList<String> amount;
    public String x; // number of transactions;
    public String TransCount;
    SharedPreferences sharedPreferences;
    ValueLineChart mCubicValueLineChart;
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
        //
        mCubicValueLineChart = (ValueLineChart) findViewById(R.id.cubiclinechart);
        LRegression();
    }

    public void LRegression(){
        Date = new ArrayList<>();
        amount = new ArrayList<>();
        //
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("User Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String x = dataSnapshot.child("Transaction count").getValue().toString();
                    //
                    sharedPreferences = getApplicationContext().getSharedPreferences(TransCount, Context.MODE_PRIVATE);
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

        sharedPreferences = getApplicationContext().getSharedPreferences(TransCount, Context.MODE_PRIVATE);
        x = sharedPreferences.getString(TransCount, null);
        //
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Transactions").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i = 0; i < Integer.parseInt(x); i++) {
                    amount.add(dataSnapshot.child("" + i).child("Amount").getValue().toString());
                    Date.add(dataSnapshot.child("" + i).child("Date").getValue().toString());
                }

                float b[] = new float[31];
                float b2[] = new float[31];
                //int month_num;
                for (int i = 0; i < Integer.parseInt(x); i++) {

                    String date = Date.get(i);
                    String[] dateParts = date.split("/");
                    String day = dateParts[0];
                    String month = dateParts[1];
                    String year = dateParts[2];

                    // First convert to Date.
                    String dateString = String.format("%s", day);
                    String dateString2 = String.format("%s-%s", year,month);
                    Date d = null;
                    Date d2 = null;
                    try {
                        d = new SimpleDateFormat("dd").parse(dateString);
                        d2 = new SimpleDateFormat("yyyy-MM").parse(dateString2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // Then get the month of the year from the Date based on specific locale.
                    String day_of_month = new SimpleDateFormat("D", Locale.ENGLISH).format(d);
                    //
                    String monthXX = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH).format(d2);

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
                    LocalDateTime now = LocalDateTime.now();
                    String month_2 = dtf.format(now);
                    //
                    try {
                        //
                        if (monthXX.equals(month_2)) {
                            switch (day_of_month) {

                                case "1":
                                    b[1] = b[1] + Float.parseFloat(amount.get(i));
                                    break;
                                case "2":
                                    b[2] = b[2] + Float.parseFloat(amount.get(i));
                                    break;
                                case "3":
                                    b[3] = b[3] + Float.parseFloat(amount.get(i));
                                    break;
                                case "4":
                                    b[4] = b[4] + Float.parseFloat(amount.get(i));
                                    break;
                                case "5":
                                    b[5] = b[5] + Float.parseFloat(amount.get(i));
                                    break;
                                case "6":
                                    b[6] = b[6] + Float.parseFloat(amount.get(i));
                                    break;
                                case "7":
                                    b[7] = b[7] + Float.parseFloat(amount.get(i));
                                    break;
                                case "8":
                                    b[8] = b[8] + Float.parseFloat(amount.get(i));
                                    break;
                                case "9":
                                    b[9] = b[9] + Float.parseFloat(amount.get(i));
                                    break;
                                case "10":
                                    b[10] = b[10] + Float.parseFloat(amount.get(i));
                                    break;
                                case "11":
                                    b[11] = b[11] + Float.parseFloat(amount.get(i));
                                    break;
                                case "12":
                                    b[12] = b[12] + Float.parseFloat(amount.get(i));
                                    break;
                                case "13":
                                    b[13] = b[13] + Float.parseFloat(amount.get(i));
                                    break;
                                case "14":
                                    b[14] = b[14] + Float.parseFloat(amount.get(i));
                                    break;
                                case "15":
                                    b[15] = b[15] + Float.parseFloat(amount.get(i));
                                    break;
                                case "16":
                                    b[16] = b[16] + Float.parseFloat(amount.get(i));
                                    break;
                                case "17":
                                    b[17] = b[17] + Float.parseFloat(amount.get(i));
                                    break;
                                case "18":
                                    b[18] = b[18] + Float.parseFloat(amount.get(i));
                                    break;
                                case "19":
                                    b[19] = b[19] + Float.parseFloat(amount.get(i));
                                    break;
                                case "20":
                                    b[20] = b[20] + Float.parseFloat(amount.get(i));
                                    break;
                                case "21":
                                    b[21] = b[21] + Float.parseFloat(amount.get(i));
                                    break;
                                case "22":
                                    b[22] = b[22] + Float.parseFloat(amount.get(i));
                                    break;
                                case "23":
                                    b[23] = b[23] + Float.parseFloat(amount.get(i));
                                    break;
                                case "24":
                                    b[24] = b[24] + Float.parseFloat(amount.get(i));
                                    break;
                                case "25":
                                    b[25] = b[25] + Float.parseFloat(amount.get(i));
                                    break;
                                case "26":
                                    b[26] = b[26] + Float.parseFloat(amount.get(i));
                                    break;
                                case "27":
                                    b[27] = b[27] + Float.parseFloat(amount.get(i));
                                    break;
                                case "28":
                                    b[28] = b[28] + Float.parseFloat(amount.get(i));
                                    break;
                                case "29":
                                    b[29] = b[29] + Float.parseFloat(amount.get(i));
                                    break;
                                case "30":
                                    b[30] = b[30] + Float.parseFloat(amount.get(i));
                                    break;
                                case "31":
                                    b[31] = b[31] + Float.parseFloat(amount.get(i));
                                    break;
                            }
                        }
                    } catch(Exception e ){}
                }
                ArrayList<Integer> DateX = new ArrayList<>(Arrays. asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31));
                ArrayList<Float> AmountY = new ArrayList<>();
                // adding data;
                for(int z = 0; z <= 30;z++){
                        AmountY.add(b[z]);
                }
                // setting data in graph;
                ValueLineSeries series = new ValueLineSeries();
                series.setColor(0xFF35ef4e);
                series.addPoint(new ValueLinePoint("", 0));
                for(int z = 0; z <= 30;z++) {
                    series.addPoint(new ValueLinePoint(""+z, b[z]));
                }
                series.addPoint(new ValueLinePoint("", 0));
                mCubicValueLineChart.addSeries(series);
                mCubicValueLineChart.startAnimation();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private static Double predictForValue(int predictForDependentVariable, ArrayList<Integer> xx, ArrayList<Float> y) {
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
}