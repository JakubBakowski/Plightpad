package com.plightpad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.animation.EasingFunction;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.data;

public class ResultsActivity extends AppCompatActivity {
    @BindView(R.id.chart)
    LineChart chart;
    List<Entry> entries = new ArrayList<Entry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);
        entries.add(new Entry(20, 40));
        entries.add(new Entry(25,56));
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setColor(getResources().getColor(R.color.th3));
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.animateY(200, Easing.EasingOption.EaseInBack);
        chart.setBorderWidth(6.0f);
        chart.setMinimumWidth(6);
        chart.invalidate(); // refresh
    }
}
