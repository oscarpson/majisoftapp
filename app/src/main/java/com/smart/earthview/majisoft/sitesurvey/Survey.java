package com.smart.earthview.majisoft.sitesurvey;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;

import com.smart.earthview.majisoft.MainActivity;
import com.smart.earthview.majisoft.R;
import com.smart.earthview.majisoft.ResponseRetro;
import com.smart.earthview.majisoft.RetrofitClient;
import com.smart.earthview.majisoft.apiService.ApiService;
import com.smart.earthview.majisoft.constant.Constants;
import com.smart.earthview.majisoft.model.MajiRepository;
import com.smart.earthview.majisoft.model.SurveyReading;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.smart.earthview.majisoft.constant.SnackClass.setErrorSnackbar;
import static com.smart.earthview.majisoft.constant.SnackClass.setSnackBar;

public class Survey extends AppCompatActivity implements View.OnClickListener {
Spinner zonespinner,spsline;
    ArrayList<String> zones,spslist;
    ArrayAdapter adapter,spsadapter;
    Button btnpost,btnsave,btnreset;
    String svyzone,svydate,svylatslongs,svymastermeter,svyserviceline,svyconnetiontype,savedRecords;
    EditText edtdate,edtlong,edtlats,edtmastermeter;
    CheckBox chkschool,chkhospital,chkcommercial,chkdomestic;
    CoordinatorLayout relativemap;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        zonespinner=findViewById(R.id.zonespinner);
        btnpost=findViewById(R.id.btnpost);
        spsline=findViewById(R.id.spsline);
        zones=new ArrayList<>();
        zones.add("Kavuu");
        zones.add("Kithyka");
        zones.add("Mutheu");
        zones.add("Musya");
        zones.add("Masaku");


        adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,zones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zonespinner.setAdapter(adapter);
        spslist=new ArrayList<>();
        spslist.add("S/N 102");
        spslist.add("S/N 105");
        spslist.add("S/N 106");
        spslist.add("S/N 107");
        spslist.add("S/N 109");
        spsadapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,spslist);
        spsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spsline.setAdapter(spsadapter);
        edtdate=findViewById(R.id.edtdate);
        edtlats=findViewById(R.id.edtlats);
        edtlong=findViewById(R.id.edtlongs);
        chkcommercial=findViewById(R.id.chkcommercial);
        chkdomestic=findViewById(R.id.chkdomestic);
        chkhospital=findViewById(R.id.chkhospital);
        chkschool=findViewById(R.id.chkschool);
        relativemap=findViewById(R.id.relativemap);
        btnreset=findViewById(R.id.btnreset);
        btnsave=findViewById(R.id.btnsave);
        final MajiRepository majiRepository=new MajiRepository(getApplicationContext());
        chkdomestic.setChecked(true);//Initialize domestic consuption as default connection
        svyconnetiontype="Domestic";
        edtmastermeter=findViewById(R.id.edtmastermeter);
        chkdomestic.setOnClickListener(this);
        chkcommercial.setOnClickListener(this);
        chkhospital.setOnClickListener(this);
        chkschool.setOnClickListener(this);

        //set date and date edittext current system time
        Date today=new Date();
        DateFormat format=new SimpleDateFormat("dd/MM/yyyy hh.mm");
        svydate=format.format(today);
        edtdate.setText(svydate);
        btnpost.setOnClickListener(this);
btnsave.setOnClickListener(this);
btnreset.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        }
});
        zonespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                svyzone=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spsline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                svyserviceline=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void safeOffline()
    {
        final MajiRepository majiRepository=new MajiRepository(getApplicationContext());
        Random random=new Random();
        svymastermeter=edtmastermeter.getText().toString().trim();
        svylatslongs=edtlats.getText().toString().trim()+","+edtlong.getText().toString().trim();
        SurveyReading surveyReading=new SurveyReading(random.nextInt(),svyzone,svydate,svyconnetiontype,svylatslongs,svymastermeter,svyserviceline);
        majiRepository.insertSurveyReading(surveyReading);
        setSnackBar(relativemap,"survey record saved successfully ");
    }

    private void postOffline(SurveyReading surveyReading, final String listProgress){
        final ProgressDialog loading = ProgressDialog.show(Survey.this, "Posting...", "Please wait..."+listProgress, false, false);

        Call<ResponseRetro>call=RetrofitClient.getRestClient().surveyForm( surveyReading.getZone(),
                surveyReading.getMdate(),
                surveyReading.getConnection(),
                surveyReading.getLatslongs(),
                surveyReading.getMastermeter(),
               surveyReading.getServiceLine());
        call.enqueue(new Callback<ResponseRetro>() {
            @Override
            public void onResponse(Call<ResponseRetro> call, Response<ResponseRetro> response) {
                loading.dismiss();
                setSnackBar(relativemap, "Survey updated successfully "+listProgress);
            }

            @Override
            public void onFailure(Call<ResponseRetro> call, Throwable t) {
            loading.dismiss();
            }
        });

    }


    private boolean inputsValidated() {
        if(edtlong.getText().length()<4 || edtlong.getText().toString().equals(" ")) {
            edtlong.setError("Please enter correct longitude");
            return false;
        }
        if (edtlats.getText().length()<4 || edtlats.getText().toString().equals(" ")){
            edtlats.setError("Please enter correct Latitude");
            return  false;
        }
        if (edtmastermeter.getText().length()<2 || edtlats.getText().toString().equals(" ")){
            edtmastermeter.setError("Please enter master meter code ");
            return  false;
        }
        return true;
    }

    private void postSurveyForm() {

        svymastermeter=edtmastermeter.getText().toString().trim();
        svylatslongs=edtlats.getText().toString().trim()+","+edtlong.getText().toString().trim();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constants.GETSTATUS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service=retrofit.create(ApiService.class);
        final ProgressDialog loading = ProgressDialog.show(Survey.this, "Posting...", "Please wait...", false, false);
        Call<ResponseRetro>call=service.surveyForm(
                svyzone,
                svydate,
                svyconnetiontype,
                svylatslongs,
                svymastermeter,
                svyserviceline);

        call.enqueue(new Callback<ResponseRetro>() {
            @Override
            public void onResponse(Call<ResponseRetro> call, Response<ResponseRetro> response) {
                loading.dismiss();
                Log.e("addrecord",response.body().toString());
                Log.e("addrecordx",response.body().getMessage());
                setSnackBar(relativemap, "Survey updated successfully ");
            }

            @Override
            public void onFailure(Call<ResponseRetro> call, Throwable t) {
                loading.dismiss();
                Log.e("addrecord","Error from survey post");
                setErrorSnackbar(relativemap, "Unable to connect to Internet kindly check your internet connection ");
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.chkdomestic:
                svyconnetiontype="Domestic";
                ClearBoxes(chkdomestic);
                break;
            case R.id.chkhospital:
                svyconnetiontype="Hospital";
                ClearBoxes(chkhospital);
                break;
            case R.id.chkcommercial:
                svyconnetiontype="Commercial";
                ClearBoxes(chkcommercial);
                break;
            case R.id.chkschool:
                svyconnetiontype="School";
                ClearBoxes(chkschool);
                break;
            case R.id.btnsave:
                if(inputsValidated()){
                    showSaveDialog("Save Reading","Ensure you have internet connection to post directly if no internet connection click offline to save readings offline");}


                break;
            case R.id.btnpost:
                showDialog("Confirm posting ","please confirm you want to post offline survey data, once data is posted it will be cleared from local storage");

                break;
            case R.id.btncancel:

                break;
        }
    }
    private void showDialog(String title, String messaged) {
        final AlertDialog dialog = new AlertDialog.Builder(Survey.this).create();
        dialog.setTitle(title);
        dialog.setMessage(messaged);
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();

                //Intent intent = new Intent(getApplicationContext(), HistoryMain.class);
                //startActivity(intent);

            }
        });

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                postOfflineData();

            }
        });
        dialog.setIcon(R.drawable.ic_tick);
        dialog.show();
    }

    private void postOfflineData() {
        final int size;
        final MajiRepository majiRepository=new MajiRepository(getApplicationContext());
        majiRepository.getSurveyReading().observe(Survey.this, new Observer<List<SurveyReading>>() {
            int p=1;
            @Override
            public void onChanged(@Nullable List<SurveyReading> surveyReadings) {
                if (surveyReadings.size() >= 1) {
                    //for (SurveyReading surveyReading : surveyReadings) {
                    for(int x=0;x<surveyReadings.size();x++){
                        savedRecords = p + "/" + surveyReadings.size() + "";
                        postOffline(surveyReadings.get(x), savedRecords);
                        Log.e("svyrecord", savedRecords);
                        Log.e("svyx", surveyReadings.get(x).getConnection() + "\t" + surveyReadings.size()+" "+p);
                        p++;

                    }
                    if(p-1==surveyReadings.size()){
                        final MajiRepository majiRepository=new MajiRepository(getApplicationContext());
                        majiRepository.deleteOfflineSurvey();
                        p=0;
                        Log.e("svydel","deletes");

                    }
                }
                else{setErrorSnackbar(relativemap,"no offline survey data available for posting");
                }
            }

        });
    }
    private void showSaveDialog(String title, String messaged) {
        final AlertDialog dialog = new AlertDialog.Builder(Survey.this).create();
        dialog.setTitle(title);
        dialog.setMessage(messaged);
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();

                //Intent intent = new Intent(getApplicationContext(), HistoryMain.class);
                //startActivity(intent);

            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Offline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO 1 SAVE THIS DATA AS A LIST OFFLINE
                safeOffline();
                //save all readings to room sqlite database
                //using separate endpoint upload list of readings to server
                //delete all data in table
            }
        });
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                postSurveyForm();




            }
        });
        dialog.setIcon(R.drawable.ic_tick);
        dialog.show();
    }
    private void ClearBoxes(CheckBox chksample) {
        chkdomestic.setChecked(false);
        chkschool.setChecked(false);
        chkhospital.setChecked(false);
        chkcommercial.setChecked(false);
        chksample.setChecked(true);
    }
}
