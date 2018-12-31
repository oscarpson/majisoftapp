package com.smart.earthview.majisoft;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smart.earthview.majisoft.analytics.AnalyticsActivity;
import com.smart.earthview.majisoft.model.LoginClass;
import com.smart.earthview.majisoft.model.MajiRepository;
import com.smart.earthview.majisoft.sitesurvey.Survey;

import java.util.List;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout meter_reader_layer;
    ImageView meterreaderimg,reconnectionimg,disconectimg,zonemapimg,analyticsimg,surveyimg;
    int loginsize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        if (isLoggedIn()) {
        meterreaderimg=findViewById(R.id.meterreaderimg);
        reconnectionimg=findViewById(R.id.reconnectionimg);
        disconectimg=findViewById(R.id.disconectimg);
        zonemapimg=findViewById(R.id.zonemapimg);
        analyticsimg=findViewById(R.id.analyticsimg);
        surveyimg=findViewById(R.id.surveyimg);
        surveyimg.setOnClickListener(this);
        zonemapimg.setOnClickListener(this);
        analyticsimg.setOnClickListener(this);
        meterreaderimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ints=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(ints);
            }
        });
        reconnectionimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ints=new Intent(getApplicationContext(),ReconnectionActivity.class);
                startActivity(ints);
            }
        });
        disconectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ints=new Intent(getApplicationContext(),DisconnectionActivity.class);
                startActivity(ints);
            }
        });
        meter_reader_layer=findViewById(R.id.meter_reader_layer);

        }
        else {
            Intent ints=new Intent(getApplicationContext(),LoginActivity.class);
            // ints.FLAG_ACTIVITY_CLEAR_TOP;
            startActivity(ints);
        }

    }
    private boolean isLoggedIn() {
        //int loginsize;
        SharedPreferences pref=getApplicationContext().getSharedPreferences("login",0);
        return  pref.getBoolean("logged",false);
        /*final MajiRepository majiRepository = new MajiRepository(getApplicationContext());
        //majiRepository.getLogin().getValue().
        majiRepository.getLogin().observe(LandingActivity.this, new Observer<List<LoginClass>>() {
            @Override
            public void onChanged(@Nullable List<LoginClass> loginClasses) {
                if (loginClasses.size()==1){
                    loginsize=loginClasses.size();
                }
                else {
                   loginsize=0;
                }
            }
        });
     if (loginsize==1){
         return true;
     }
     else {
         return false;
     }

        //return true;*/
    }

    @Override
    public void onClick(View v) {
        Intent ints;
        switch (v.getId()){
            case R.id.zonemapimg:
                ints=new Intent(getApplicationContext(),MeterLocation.class);
                startActivity(ints);
                break;
            case R.id.analyticsimg:
                ints=new Intent(getApplicationContext(),AnalyticsActivity.class);
                startActivity(ints);
                break;
            case R.id.surveyimg:
                ints=new Intent(getApplicationContext(),Survey.class);
                startActivity(ints);
                break;
        }
    }
}
