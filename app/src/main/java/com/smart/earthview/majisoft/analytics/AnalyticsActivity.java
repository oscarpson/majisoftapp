package com.smart.earthview.majisoft.analytics;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.smart.earthview.majisoft.R;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsActivity extends AppCompatActivity {
    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         barChart = (BarChart) findViewById(R.id.barchart);
        ArrayList<BarEntry> BarEntry = new ArrayList<>();
        BarEntry.add(new BarEntry(3f, 0));
        BarEntry.add(new BarEntry(4f, 1));
        BarEntry.add(new BarEntry(6f, 2));
        BarEntry.add(new BarEntry(8f, 3));
        BarEntry.add(new BarEntry(7f, 4));

        BarDataSet dataSet = new BarDataSet(BarEntry, "Zones");
        ArrayList<String> labels = new ArrayList<>();

        labels.add("Kavuu");
        labels.add("Kithyka");
        labels.add("Mutheu");
        labels.add("Musya");
        labels.add("Masaku");
        barChart.animateY(5000);
        barChart.setDescription("Amount per m3 Litres");
        //BarData data = new BarData(labels,dataSet);
       /* ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(8f, 0));
        bargroup1.add(new BarEntry(2f, 1));
        bargroup1.add(new BarEntry(5f, 2));
        bargroup1.add(new BarEntry(20f, 3));
        bargroup1.add(new BarEntry(15f, 4));
        bargroup1.add(new BarEntry(19f, 5));

// create BarEntry for Bar Group 1
        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(6f, 0));
        bargroup2.add(new BarEntry(10f, 1));
        bargroup2.add(new BarEntry(5f, 2));
        bargroup2.add(new BarEntry(25f, 3));
        bargroup2.add(new BarEntry(4f, 4));
        bargroup2.add(new BarEntry(17f, 5));

// creating dataset for Bar Group1
        BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Bar Group 1");

//barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

// creating dataset for Bar Group 2
        BarDataSet barDataSet2 = new BarDataSet(bargroup2, "Bar Group 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        //
        ArrayList<BarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        //dataSets.add(dataSet);
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);*/
        //

        BarData data = new BarData(labels,  dataSet);
       // BarData bd2=new BarData(labels,dataSets)
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setData(data);
    }

}
