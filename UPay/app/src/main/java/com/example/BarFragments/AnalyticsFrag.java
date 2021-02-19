package com.example.BarFragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AnalyticsFrag extends Fragment {
TextView textView;
TextView textView2;
TextView textViewIncome;
ImageButton imageButton;
ImageButton imageButton2;
EditText editText;
Animation animation;
CalendarView calendarView;
ValueLineChart mCubicValueLineChart;
ValueLineChart mCubicValueLineChart2;
CardView cardView;
//
double income;
//
SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    //
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    // Graphs;
    // For week;
    //
   ArrayList <String> Date;
   ArrayList <String>  amount;


    public AnalyticsFrag() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromHome();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);
        textView = view.findViewById(R.id.textView3);
        textViewIncome = view.findViewById(R.id.income_val);
        //
        mCubicValueLineChart = view.findViewById(R.id.cubiclinechart);
        mCubicValueLineChart2 = view .findViewById(R.id.cubiclinechart2);
        //
        editText = view.findViewById(R.id.editTextTextPersonName);
        imageButton = view.findViewById(R.id.imageButton5);
        imageButton2 = view.findViewById(R.id.imageButtonREC);
        textView2 = view.findViewById(R.id.textView13);
        //
        editText.setVisibility(View.INVISIBLE);
        imageButton.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        //
        cardView = view.findViewById(R.id.income);
        calendarView = view.findViewById(R.id.calendarView);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.open_animation);
        animation.setDuration(1100);
        textView.setText("$1982.20");
        textView.startAnimation(animation);
        //
        setCalendarView();
        LineChart();
       // LineChart2();
        Flip();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Please fill Fields",Toast.LENGTH_SHORT).show();
                }
                else {
                    income = Double.parseDouble(editText.getText().toString());
                    textViewIncome.setText("$" + income);
                    //
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    Add(user.getUid(),income);
                    //
                    Flip2();
                    editText.setText("");
                }
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        //
        // This if Statement is used for those who haven't had any previously added income;
        if (FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("User Data").child("income") != null) {

            FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("User Data").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String val;
                    try {
                        val = dataSnapshot.child("income").getValue().toString();
                        textViewIncome.setText("$" + val);
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
        // recommendation button;
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Toast.makeText(getContext(),"TIPPY TEST",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void Spree(){
       // Spree is Increase or Decrease in Spending;

    }

    public String x; // number of transactions;
    public String TransCount;
   public void getDataFromHome() {
       user = FirebaseAuth.getInstance().getCurrentUser();
       FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("User Data").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               try {
                   // => to local DATABASE;
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

       Date = new ArrayList<>();
       amount = new ArrayList<>();



       FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Transactions").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
                   for (int i = 0; i < Integer.parseInt(x); i++) {
                       amount.add(dataSnapshot.child("" + i).child("Amount").getValue().toString());
                       Date.add(dataSnapshot.child("" + i).child("Date").getValue().toString());
                   }
               Float a1 = 0.0f;;
               Float a2 = 0.0f;;
               Float a3 = 0.0f;;
               Float a4 = 0.0f;;
               Float a5 = 0.0f;;
               Float a6 = 0.0f;;
               Float a7 = 0.0f;
               for (int i = 0; i < Integer.parseInt(x); i++) {
//                   Toast.makeText(getContext(), Date.get(i), Toast.LENGTH_SHORT).show();
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
                       Log.d("TEST1",d.toString());
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
                   // Then get the day of week from the Date based on specific locale.
                   String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(d);
                   Log.d("TEST2",dayOfWeek);
                   //
                   switch (dayOfWeek){

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
                       case "Friday" :
                           try {
                               a5 = a5 + Float.parseFloat(amount.get(i));
                           }catch(Exception e){
                               Toast.makeText(getContext(), "a5"+a5+"\n=>amount"+amount.get(i), Toast.LENGTH_SHORT).show();
                           }
                           break;
                       case "Saturday":
                           a6 = a6 + Float.parseFloat(amount.get(i));
                           break;
                       case "Sunday":
                           a7 = a7 + Float.parseFloat(amount.get(i));
                           break;

                   }
               }

               ValueLineSeries series = new ValueLineSeries();
               series.setColor(0xFFD267E4);
               series.addPoint(new ValueLinePoint("", 0));

               series.addPoint(new ValueLinePoint("M", a1));
               series.addPoint(new ValueLinePoint("T", a2));
               series.addPoint(new ValueLinePoint("W", a3));
               series.addPoint(new ValueLinePoint("TH",a4));
               series.addPoint(new ValueLinePoint("F", a5));
               series.addPoint(new ValueLinePoint("S", a6));
               series.addPoint(new ValueLinePoint("S", a7));
               series.addPoint(new ValueLinePoint("", 0));

               mCubicValueLineChart2.addSeries(series);
               mCubicValueLineChart2.startAnimation();
           }
           @Override
           public void onCancelled(DatabaseError error) {
           }
       });
   }


    public void LineChart(){
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF2196F3);
        series.addPoint(new ValueLinePoint("", 0));
        series.addPoint(new ValueLinePoint("Jan", 2f));
        series.addPoint(new ValueLinePoint("Feb", 3.4f));
        series.addPoint(new ValueLinePoint("Mar", .4f));
        series.addPoint(new ValueLinePoint("Apr", 1.2f));
        series.addPoint(new ValueLinePoint("Mai", 2.6f));
        series.addPoint(new ValueLinePoint("Jun", 1.0f));
        series.addPoint(new ValueLinePoint("Jul", 3.5f));
        series.addPoint(new ValueLinePoint("Aug", 1250.43f));
        series.addPoint(new ValueLinePoint("Sep", 2.4f));
        series.addPoint(new ValueLinePoint("Oct", 3.4f));
        series.addPoint(new ValueLinePoint("Nov", .4f));
        series.addPoint(new ValueLinePoint("Dec", 1.3f));
        series.addPoint(new ValueLinePoint("", 0));

        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
    }


    public void Flip(){
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

    public void Flip2(){
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

    public void setCalendarView() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                // i is the year;
                // i2 the day;
                Toast.makeText(getContext(), ""+ i2, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Add(String userId,double income) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> values = new HashMap<>();
        values.put("income", income);
        mDatabase.child("Users").child(userId).child("User Data").updateChildren(values);
    }
}