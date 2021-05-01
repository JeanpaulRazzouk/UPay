package com.example.BarFragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapters.Adapter;
import com.example.upay.BottomSheetCal;
import com.example.upay.EventDeco;
import com.example.upay.PurchaseItems;
import com.example.upay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AnalyticsFrag extends Fragment {
    TextView textView;
    TextView textView2;
    TextView textViewIncome;
    TextView percentage;
    TextView textView3;
    ImageButton imageButton;
    ImageButton imageButton2;
    EditText editText;
    Animation animation;
    ValueLineChart mCubicValueLineChart;
    ValueLineChart mCubicValueLineChart2;
    CircularProgressBar circularProgressBar;
    CardView cardView;
    MaterialCalendarView materialCalendarView;
    double income;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    ArrayList<String> Date;
    ArrayList<String> amount;
    ArrayList<String> Date2;
    ArrayList<String> amount2;
    ArrayList<String> Date3;
    ArrayList<String> amount3;
    String value; // to avoid bottom sheet loop

    public AnalyticsFrag() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);
        try {
            textView = view.findViewById(R.id.textView3);
            textViewIncome = view.findViewById(R.id.income_val);
            percentage = view.findViewById(R.id.percentage);
            textView3 = view.findViewById(R.id.exp_val);
            //
            mCubicValueLineChart = view.findViewById(R.id.cubiclinechart);
            mCubicValueLineChart2 = view.findViewById(R.id.cubiclinechart2);
            circularProgressBar = view.findViewById(R.id.pb_one);
            //
            editText = view.findViewById(R.id.editTextTextPersonName);
            imageButton = view.findViewById(R.id.imageButton5);
            imageButton2 = view.findViewById(R.id.imageButtonREC);
            textView2 = view.findViewById(R.id.textView13);
            //
            materialCalendarView = view.findViewById(R.id.calendarView);
            //
            editText.setVisibility(View.INVISIBLE);
            imageButton.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);
            //
            cardView = view.findViewById(R.id.income);
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.open_animation);
            animation.setDuration(1100);
            textView.setText("$0");
            textView.startAnimation(animation);
            //
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editText.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Please fill Fields", Toast.LENGTH_SHORT).show();
                    } else {
                        income = Double.parseDouble(editText.getText().toString());
                        textViewIncome.setText("$" + income);
                        //
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        Add(user.getUid(), income);
                        //
                        Flip2();
                        editText.setText("");
                    }
                }
            });
            // Recommendation button;
            imageButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (textViewIncome.getText().toString().equals("Press to add")) {
                        Toast.makeText(getContext(), "Please add income", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent i = new Intent(getContext(), Forecast.class);
                        startActivity(i);
                    }
                }
            });

            // methods();
            user = FirebaseAuth.getInstance().getCurrentUser();
            // This if Statement is used for those who haven't had any previously added income;
            if (FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Income").child("income") != null) {

                FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String val;
                        String Currency = dataSnapshot.child("Currency").child("Currency").getValue().toString();
                        try {
                            val = dataSnapshot.child("Income").child("income").getValue().toString();
                            if (Currency.equals("$")) {
                                textViewIncome.setText("$" + val);
                            }
                            else if (Currency.equals("€")){
                                double v = 0.83*Double.parseDouble(val);
                                textViewIncome.setText("€" + v);
                            }
                            else if (Currency.equals("$CA")){
                                double v = 1.23*Double.parseDouble(val);
                                textViewIncome.setText("$CA" + v);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

                try {
                    Flip();
                    getDataFromHome();
                    calendar();
                    Spree();
                    CurrentMonth();
                } catch (Exception e) {
                }
            }
        }catch(Exception e){}
        return view;
    }


    public void Spree() {

        Date3 = new ArrayList<>();
        amount3 = new ArrayList<>();
        // Spree is Increase or Decrease in Spending
        String formattedString;
        LocalDate localDate = null;//For reference
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
            formattedString = localDate.format(formatter); // present month string;
            int p_mo = Integer.parseInt(formattedString) - 1; // past month

            String past_month = "" + (p_mo < 10 ? ("0" + p_mo) : (p_mo)); // past month string;

            for (int i = 0; i < 5; i++) {
                FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Float current_month_val = 0.0f;
                        Float past_month_val = 0.0f;
                        //
                        String x = dataSnapshot.child("User Data").child("Transaction count").getValue().toString();
                        String Currency = dataSnapshot.child("Currency").child("Currency").getValue().toString();
                        //
                        for (int i = 0; i < Integer.parseInt(x); i++) {
                            amount3.add(dataSnapshot.child("Transactions").child("" + i).child("Amount").getValue().toString());
                            Date3.add(dataSnapshot.child("Transactions").child("" + i).child("Date").getValue().toString());
                        }
                        //
                        for (int j = 0; j < Integer.parseInt(x); j++) {
                            String date = Date3.get(j);
                            String[] dateParts = date.split("/");
                            String month = dateParts[1];
                            String dateString = String.format("%s", month);

                            if (dateString.equals(formattedString)) {
                                current_month_val = current_month_val + Float.parseFloat(amount3.get(j));
                            } else if (dateString.equals(past_month)) {
                                past_month_val = past_month_val + Float.parseFloat(amount3.get(j));
                            }
                        }
                        Float perc = (1 - (current_month_val / past_month_val)) * 100;
                        int perc_final = Math.round(perc);

                        Float perc2 = ((past_month_val / current_month_val) - 1) * 100;
                        int perc_final_2 = Math.round(perc2);

                        if (current_month_val > past_month_val && current_month_val != null && past_month_val != null) {
                            float fin = current_month_val - past_month_val;

                            if (Currency.equals("$")) {
                                textView3.setText("+" + "$" + fin);
                            }
                            else if (Currency.equals("€")){
                                textView3.setText("+" + "€"+  new DecimalFormat("##.##").format(0.83*fin));
                            }
                            else if (Currency.equals("$CA")){
                                textView3.setText("+" + "$CA" +  new DecimalFormat("##.##").format(1.23*fin));
                            }

                            if (current_month_val == 0.0f || past_month_val == 0.0f) {
                                percentage.setText("0%");
                            } else {
                                percentage.setText(perc_final + "%");
                                circularProgressBar.setProgressWithAnimation((int) perc_final, Long.valueOf(3000)); // 3 sec;
                                circularProgressBar.setProgressBarColor(Color.parseColor("#FF1D47"));
                            }
                        } else if (current_month_val < past_month_val && current_month_val != null && past_month_val != null) {
                            float fin = past_month_val - current_month_val;

                            if (Currency.equals("$")) {
                                textView3.setText("-" + "$" + fin);
                            }
                            else if (Currency.equals("€")){
                                textView3.setText("-" + "€"+   new DecimalFormat("##.##").format(0.83*fin));
                            }
                            else if (Currency.equals("$CA")){
                                textView3.setText("-" + "$CA" +   new DecimalFormat("##.##").format(1.23*fin));
                            }

                            if (current_month_val == 0.0f || past_month_val == 0.0f) {
                                percentage.setText("0%");
                            } else {
                                percentage.setText(perc_final_2 + "%");
                                circularProgressBar.setProgressWithAnimation((int) perc_final_2, Long.valueOf(3000)); // 3 sec;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    }

    public void getDataFromHome()
    {
//        ProgressDialog pd = new ProgressDialog(getContext(),R.style.MyAlertDialogStyle);
//        pd.setMessage("One sec...");
//        pd.getWindow().setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.dialog_bg));
//        pd.show();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //
        Date = new ArrayList<>();
        amount = new ArrayList<>();
        //
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String x = dataSnapshot.child("User Data").child("Transaction count").getValue().toString();
                try {
                    for (int i = 0; i < Integer.parseInt(x); i++) {
                        amount.add(dataSnapshot.child("Transactions").child("" + i).child("Amount").getValue().toString());
                        Date.add(dataSnapshot.child("Transactions").child("" + i).child("Date").getValue().toString());
                    }

                }catch(Exception e){}
                // First Graph;
                Float a1 = 0.0f;
                ;
                Float a2 = 0.0f;
                ;
                Float a3 = 0.0f;
                ;
                Float a4 = 0.0f;
                ;
                Float a5 = 0.0f;
                ;
                Float a6 = 0.0f;
                ;
                Float a7 = 0.0f;

                for (int i = 0; i < Integer.parseInt(x); i++) {
                    String date = Date.get(i);
                    String[] dateParts = date.split("/");
                    String day = dateParts[0];
                    String month = dateParts[1];
                    String year = dateParts[2];

                    String dateString = String.format("%s-%s-%s", year, month, day);
                    //
                    // First convert to Date. This is one of the many ways.
                    LocalDate from = LocalDate.parse(dateString); // Date from payments;
                    LocalDate to = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // current date; (moving)

                    long days = ChronoUnit.DAYS.between(from, to);

                    int mo = Calendar.getInstance().get(Calendar.MONTH) + 1;
                    // TODO all analytics frame drops;
                    if (days <= 7 && Integer.parseInt(month) == mo) {
                        Date d = null;
                        try {
                            d = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        // Then get the day of week from the Date based on specific locale.
                        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(d);
                        //
                        switch (dayOfWeek) {
                            case "Monday":
                                a1 = a1 + Float.parseFloat(amount.get(i));
                                break;
                            case "Tuesday":
                                a2 = a2 + Float.parseFloat(amount.get(i));
                                break;
                            case "Wednesday":
                                a3 = a3 + Float.parseFloat(amount.get(i));
                                break;
                            case "Thursday":
                                a4 = a4 + Float.parseFloat(amount.get(i));
                                break;
                            case "Friday":
                                a5 = a5 + Float.parseFloat(amount.get(i));
                                break;
                            case "Saturday":
                                a6 = a6 + Float.parseFloat(amount.get(i));
                                break;
                            case "Sunday":
                                a7 = a7 + Float.parseFloat(amount.get(i));
                                break;
                        }
                    }
                }
                ValueLineSeries series = new ValueLineSeries();
                series.setColor(0xFFD267E4);
                series.addPoint(new ValueLinePoint("", 0));
                series.addPoint(new ValueLinePoint("M", a1));
                series.addPoint(new ValueLinePoint("T", a2));
                series.addPoint(new ValueLinePoint("W", a3));
                series.addPoint(new ValueLinePoint("TH", a4));
                series.addPoint(new ValueLinePoint("F", a5));
                series.addPoint(new ValueLinePoint("S", a6));
                series.addPoint(new ValueLinePoint("S", a7));
                series.addPoint(new ValueLinePoint("", 0));
                mCubicValueLineChart2.addSeries(series);
                mCubicValueLineChart2.startAnimation();

                // Second Graph;
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

                for (int i = 0; i < Integer.parseInt(x); i++) {

                    String date = Date.get(i);
                    String[] dateParts = date.split("/");
                    String day = dateParts[0];
                    String month = dateParts[1];
                    String year = dateParts[2];

                    // First convert to Date. This is one of the many ways.
                    String dateString = String.format("%s-%s-%s", year, month, day);

                    LocalDate from = LocalDate.parse(dateString); // Date from payments;
                    LocalDate to = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // current date; (moving)

                    long y = ChronoUnit.YEARS.between(from, to);


                    int yo = Calendar.getInstance().get(Calendar.YEAR);

                    if (y < 1 && Integer.parseInt(year) == yo) {
                        Date d = null;
                        try {
                            d = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        // Then get the month of the year from the Date based on specific locale.
                        String month_of_year = new SimpleDateFormat("M", Locale.ENGLISH).format(d);

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
                    }
                }
                ValueLineSeries se = new ValueLineSeries();
                se.setColor(0xFF2196F3);
                se.addPoint(new ValueLinePoint("", 0));
                se.addPoint(new ValueLinePoint("Jan", m1));
                se.addPoint(new ValueLinePoint("Feb", m2));
                se.addPoint(new ValueLinePoint("Mar", m3));
                se.addPoint(new ValueLinePoint("Apr", m4));
                se.addPoint(new ValueLinePoint("Mai", m5));
                se.addPoint(new ValueLinePoint("Jun", m6));
                se.addPoint(new ValueLinePoint("Jul", m7));
                se.addPoint(new ValueLinePoint("Aug", m8));
                se.addPoint(new ValueLinePoint("Sep", m9));
                se.addPoint(new ValueLinePoint("Oct", m10));
                se.addPoint(new ValueLinePoint("Nov", m11));
                se.addPoint(new ValueLinePoint("Dec", m12));
                se.addPoint(new ValueLinePoint("", 0));
                mCubicValueLineChart.addSeries(se);
                mCubicValueLineChart.startAnimation();
                //
               // pd.hide();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void Flip() {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(cardView, "scaleX", 1f, 0f);
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(cardView, "scaleX", 0f, 1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        cardView.setCardBackgroundColor(0xFFffffff);
                        oa2.start();
                        editText.setVisibility(View.VISIBLE);
                        imageButton.setVisibility(View.VISIBLE);
                        textView2.setVisibility(View.VISIBLE);
                    }
                });
                oa1.start();
            }
        });
    }

    public void Flip2() {
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(cardView, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(cardView, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                editText.setVisibility(View.INVISIBLE);
                imageButton.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                cardView.setCardBackgroundColor(0xFFD267E4);
                oa2.start();
            }
        });
        oa1.start();
    }

    public void calendar() {
        Date2 = new ArrayList<>();
        amount2 = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String x = dataSnapshot.child("User Data").child("Transaction count").getValue().toString();
                for (int i = 0; i < Integer.parseInt(x); i++) {
                    amount2.add(dataSnapshot.child("Transactions").child("" + i).child("Amount").getValue().toString());
                    Date2.add(dataSnapshot.child("Transactions").child("" + i).child("Date").getValue().toString());
                }
                String dateSX2[] = new String[Integer.parseInt(x)];
                // The following is to add dots on the transaction dates;
                ArrayList<CalendarDay> calendarDays = new ArrayList<>();
                for (int j = 0; j < Integer.parseInt(x); j++) {
                    String r = Date2.get(j);
                    String[] dateP = r.split("/");
                    String da = dateP[0];
                    String mo = dateP[1];
                    String ye = dateP[2];

                    dateSX2[j] = String.format("%s-%s-%s", ye, mo, da);

                    CalendarDay calendarDay = CalendarDay.from(Integer.parseInt(ye), Integer.parseInt(mo) - 1, Integer.parseInt(da));
                    calendarDays.add(calendarDay);
                }

                for (int i = 0; i < Integer.parseInt(x); i++) {
                    materialCalendarView.addDecorator(new EventDeco(calendarDays));
                }


                materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date.getDate());
                        int year = calendar.get(Calendar.YEAR);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH) + 1;

                        String FIN = year + "-" + (month < 10 ? ("0" + month) : (month)) + "-" + (day < 10 ? ("0" + day) : (day));

                        for (int j = 0; j < Integer.parseInt(x); j++) {
                            if (dateSX2[j].equals(FIN)) {
                                value = dateSX2[j];
                            }
                        }
                        try {
                            if (value.equals(FIN)) {
                                BottomSheetCal bottomSheet = new BottomSheetCal(FIN);
                                bottomSheet.show(getFragmentManager(), "TAG");
                            }
                        }catch (Exception e)
                        {}
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Add(String userId, double income) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> values = new HashMap<>();
        values.put("income", income);
        mDatabase.child("Users").child(userId).child("Income").updateChildren(values);
    }

    public void CurrentMonth(){
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String x = dataSnapshot.child("User Data").child("Transaction count").getValue().toString();
                String Currency = dataSnapshot.child("Currency").child("Currency").getValue().toString();
                for (int i = 0; i < Integer.parseInt(x); i++) {
                    amount.add(dataSnapshot.child("Transactions").child("" + i).child("Amount").getValue().toString());
                    Date.add(dataSnapshot.child("Transactions").child("" + i).child("Date").getValue().toString());
                }
                float fin_val = 0.0f;
                for (int i = 0; i < Integer.parseInt(x); i++) {
                    String date = Date.get(i);
                    String[] dateParts = date.split("/");
                    String month = dateParts[1];
                    String year = dateParts[2];

                    // First convert to Date. This is one of the many ways.
                    String dateString = String.format("%s-%s", year, month);
                    Date d = null;
                    try {
                        d = new SimpleDateFormat("yyyy-MM").parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    String month_of_year = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH).format(d);

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
                    LocalDateTime now = LocalDateTime.now();
                    String month_2 = dtf.format(now);

                    if (month_of_year.equals(month_2)){
                        fin_val = fin_val + Float.parseFloat(amount.get(i));
                    }
                }
                if (Currency.equals("$")) {
                    textView.setText("$"+new DecimalFormat("##.##").format(fin_val));//
                }
                else if (Currency.equals("€")){
                    double val =  (0.83*Double.parseDouble(""+fin_val));
                    textView.setText("€"+new DecimalFormat("##.##").format(val));//
                }
                else if (Currency.equals("$CA")){
                    double val = (1.23*Double.parseDouble(""+fin_val));
                    textView.setText("$CA"+new DecimalFormat("##.##").format(val));//
                }
                user = FirebaseAuth.getInstance().getCurrentUser();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                HashMap<String, Object> values = new HashMap<>();
                values.put("This Month", ""+fin_val);
                mDatabase.child("Users").child(user.getUid()).child("User Data").updateChildren(values);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
