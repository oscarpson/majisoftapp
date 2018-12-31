package com.smart.earthview.majisoft;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smart.earthview.majisoft.apiService.ApiService;
import com.smart.earthview.majisoft.constant.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.smart.earthview.majisoft.constant.SnackClass.setErrorSnackbar;
import static com.smart.earthview.majisoft.constant.SnackClass.setSnackBar;

public class DisconnectionActivity extends AppCompatActivity {
    ArrayList<String> zones,statusArray,mreaderArray;
    ArrayAdapter adapter,adapter2,mreaderAdapter;
    Spinner zoneSpinner,mstatus,mreader;
    Marker myMarker;
    ArrayList<CustomerClass> customerList;
    TextView txtaccno,txtcname,txtcdate;
    private GoogleMap googleMap;
    MapView mapView;
    Button btnsave,btnmsearch;
    EditText edtSearch,edtmessage;
    String meterSearch,discMessage,accNumber,discReason,disconnectedBy,prdate,readerlocation;
    SharedPreferences mylocation;
    LatLng meterLocation;
    Constants constants;
    CoordinatorLayout relativemap;
    private static final int REQUEST_LOCATION_PERMISSION_CODE = 101;
    private GoogleApiClient googleApiClient;
    final static String ORDERS_URL = "http://olsen.joslabs.co.ke/mobileapp/getmyorders.php";
    private static final String TAG = "MainActivity";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disconnection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mapView=findViewById(R.id.map);

        mapView.onCreate(savedInstanceState);

        mapView.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION_CODE);
        }
        txtaccno=findViewById(R.id.txtaccno);
        txtcname=findViewById(R.id.txtcname);
       // txtcdate=findViewById(R.id.txtcdate);
        edtSearch=findViewById(R.id.editTextsearch);
        edtmessage=findViewById(R.id.edtmessage);
        zoneSpinner=findViewById(R.id.zonespinner);
        relativemap =  findViewById(R.id.relativemap);
        mstatus=findViewById(R.id.mstatus);
        mreader=findViewById(R.id.mreader);
        constants=new Constants();
        statusArray=new ArrayList<>();
        statusArray.add("Kevin Kihara");
        statusArray.add("Bruce Obwari");
        statusArray.add("Griffin Marita");
        statusArray.add("Charles Kaloki");
        statusArray.add("Eric Mwenda");

        adapter2=new ArrayAdapter(this,android.R.layout.simple_spinner_item,statusArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mstatus.setAdapter(adapter2);
        mstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                disconnectedBy=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mreaderArray=new ArrayList<>();
        mreaderArray.add("Overdue");
        mreaderArray.add("Illegal connection");
        mreaderArray.add("Company request");
        mreaderArray.add("Missuse & Violations");

        mreaderAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,mreaderArray);
        mreaderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mreader.setAdapter(mreaderAdapter);
        mreader.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                discReason=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //storing arraylist customer
        customerList=new ArrayList<>();
        customerList.add(new CustomerClass("0001","00100","Kitwasco","12/12/2017","Kavuu","-1.381811,38.002383"));
        customerList.add(new CustomerClass("0002","00200","Park side villa","12/12/2017","Kithyka","-1.366263,38.003794"));
        customerList.add(new CustomerClass("0003","00300","Kitui mtc","12/08/2018","Mutheu","-1.384612,38.010665"));
        customerList.add(new CustomerClass("0004","00400","Kitui water institute","12/08/2018","Musya","-1.385298,38.007618"));
        customerList.add(new CustomerClass("0005","00500","Kunda Kindu","12/08/2018","Masaku","-1.372623,38.008870"));
        //PopulateData();
        btnmsearch=findViewById(R.id.btnsearch);
        btnsave=findViewById(R.id.btnregister);
        btnmsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meterSearch=edtSearch.getText().toString();
                for(int x=0;x<customerList.size();x++) {
                    if (customerList.get(x).meter_no.contains(meterSearch))
                    {
                        txtaccno.setText(customerList.get(x).acc_no);
                        txtcname.setText(customerList.get(x).userName);
                        //txtcdate.setText(customerList.get(x).prDate);
                        String[] latslong = customerList.get(x).meter_location.split(",");
                        Log.e("coods", latslong[0] + "and longs\n" + latslong[1]);
                        String lats = latslong[0];
                        float lat = Float.valueOf(latslong[0]);
                        float llong = Float.valueOf(latslong[1]);
                        LatLng petrol = new LatLng(lat, llong);
                        meterLocation=petrol;
                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions().position(petrol).title(customerList.get(x).userName).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maphere)));
                        //myMarker=googleMap.addMarker(new MarkerOptions().position(petrol).title("Previous position").snippet("@joslas.co.ke").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maphere)));

                        CameraPosition cameraPosition=new CameraPosition.Builder().target(petrol).zoom(15).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        googleMap.addCircle(new CircleOptions()
                                .center(petrol)
                                .radius(250)
                                .strokeWidth(0f)
                                .fillColor(0x1100CCFF) //this is a half transparent blue, change "88" for the transparency
                                .strokeColor(R.color.colorPrimaryDark)
                                .strokeWidth(3)
                        );

                    }
                }
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO UNCOMMENT THIS IN PRODUCTION
                //String mlocation = mylocation.getString("location", null);


               saveDisconnection();
                }

        });
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        return false;

                    }
                });

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Intent meterLocation=new Intent(getApplicationContext(),MeterLocation.class);
                        startActivity(meterLocation);
                    }
                });
                googleMap = mMap;
                String[] latslong = customerList.get(1).meter_location.split(",");
                Log.e("coods", latslong[0] + "and longs\n" + latslong[1]);
                String lats = latslong[0];
                float lat = Float.valueOf(latslong[0]);
                float llong = Float.valueOf(latslong[1]);
                LatLng petrol = new LatLng(lat, llong);

                CameraPosition cameraPosition=new CameraPosition.Builder().target(petrol).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    private void saveDisconnection() {
        if (inputsValidated()) {
            discMessage = edtmessage.getText().toString().trim();
            accNumber = txtaccno.getText().toString().trim();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.GETSTATUS_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiService service = retrofit.create(ApiService.class);
            final ProgressDialog loading = ProgressDialog.show(DisconnectionActivity.this, "Requesting...", "Please wait...", false, false);
            Call<ResponseRetro> call = service.disconnect(
                    accNumber,
                    accNumber,
                    discReason,
                    disconnectedBy,
                    discMessage);

            call.enqueue(new Callback<ResponseRetro>() {
                @Override
                public void onResponse(Call<ResponseRetro> call, Response<ResponseRetro> response) {
                    loading.dismiss();
                    Log.e("addrecord", response.body().toString());
                    Log.e("addrecordx", response.body().getMessage());
                    setSnackBar(relativemap,response.body().getMessage());
                }

                @Override
                public void onFailure(Call<ResponseRetro> call, Throwable t) {
                    loading.dismiss();
                    Log.e("addrecord", t.getMessage());
                    setErrorSnackbar(relativemap, "Unable to connect to Internet kindly check your internet connection ");

                }
            });

        }
    }

    private boolean inputsValidated() {
        if(edtmessage.getText().toString().equals("")){
            edtmessage.setError("Please enter message ");
            return false;
        }
        if(txtaccno.getText().toString().trim().equals("")||edtSearch.getText().toString().equals("")){
            setErrorSnackbar(relativemap,"Please search meter No for account before saving readings");
            return false;
        }
        return  true;
    }

}
