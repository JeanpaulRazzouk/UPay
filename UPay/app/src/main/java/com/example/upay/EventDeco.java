package com.example.upay;

import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;

public class EventDeco extends AppCompatActivity implements DayViewDecorator {

    private final int color = Color.parseColor("#2196F3");
    private final ArrayList<CalendarDay> dates;

    public EventDeco(ArrayList<CalendarDay> dates) {

        this.dates = dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {

        view.addSpan(new DotSpan(10, color));

    }
}