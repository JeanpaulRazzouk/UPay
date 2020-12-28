package com.example.BarFragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

public class AnalyticsFrag extends Fragment {
TextView textView;
TextView textView2;
TextView textViewIncome;
ImageButton imageButton;
EditText editText;
Animation animation;
CalendarView calendarView;
ValueLineChart mCubicValueLineChart;
ValueLineChart mCubicValueLineChart2;
CardView cardView;
int income;
//
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
        textView = view.findViewById(R.id.textView3);
        textViewIncome = view.findViewById(R.id.income_val);
        //
        mCubicValueLineChart = view.findViewById(R.id.cubiclinechart);
        mCubicValueLineChart2 = view .findViewById(R.id.cubiclinechart2);
        //
        editText = view.findViewById(R.id.editTextTextPersonName);
        imageButton = view.findViewById(R.id.imageButton5);
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
        textView.setText("\n$1982.20");
        textView.startAnimation(animation);
        //
        setCalendarView();
        LineChart();
        LineChart2();
        Flip();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Please fill Fields",Toast.LENGTH_SHORT).show();
                }
                else {
                    income = Integer.parseInt(editText.getText().toString());
                    textViewIncome.setText("$" + income);
                    Flip2();
                    editText.setText(null);
                }
            }
        });
        return view;
    }

    public void Spree(){
       // Spree is Increase or Decrease in Spending;

    }

    public void LineChart(){
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF2196F3);

        series.addPoint(new ValueLinePoint("Jan", 2.4f));
        series.addPoint(new ValueLinePoint("Feb", 3.4f));
        series.addPoint(new ValueLinePoint("Mar", .4f));
        series.addPoint(new ValueLinePoint("Apr", 1.2f));
        series.addPoint(new ValueLinePoint("Mai", 2.6f));
        series.addPoint(new ValueLinePoint("Jun", 1.0f));
        series.addPoint(new ValueLinePoint("Jul", 3.5f));
        series.addPoint(new ValueLinePoint("Aug", 2.4f));
        series.addPoint(new ValueLinePoint("Sep", 2.4f));
        series.addPoint(new ValueLinePoint("Oct", 3.4f));
        series.addPoint(new ValueLinePoint("Nov", .4f));
        series.addPoint(new ValueLinePoint("Dec", 1.3f));

        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
    }

    public void LineChart2(){
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFFD267E4);

        series.addPoint(new ValueLinePoint("M", 2.4f));
        series.addPoint(new ValueLinePoint("T", 3.4f));
        series.addPoint(new ValueLinePoint("W", .4f));
        series.addPoint(new ValueLinePoint("TH", 1.2f));
        series.addPoint(new ValueLinePoint("F", 2.6f));
        series.addPoint(new ValueLinePoint("S", 1.0f));
        series.addPoint(new ValueLinePoint("S", 3.5f));

        mCubicValueLineChart2.addSeries(series);
        mCubicValueLineChart2.startAnimation();
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
}