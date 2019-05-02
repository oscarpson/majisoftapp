package com.smart.earthview.majisoft;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
import com.smart.earthview.majisoft.analytics.AnalyticsActivity;
import com.smart.earthview.majisoft.apiService.ApiService;
import com.smart.earthview.majisoft.constant.Constants;
import com.smart.earthview.majisoft.geofence.GeoFenceMeter;
import com.smart.earthview.majisoft.geofence.LastFence;
import com.smart.earthview.majisoft.geofence.MeterFence;
import com.smart.earthview.majisoft.meterStatus.MeterStatus;
import com.smart.earthview.majisoft.meterStatus.StatusClass;
import com.smart.earthview.majisoft.model.LoginClass;
import com.smart.earthview.majisoft.model.MajiRepository;
import com.smart.earthview.majisoft.model.MeterReading;
import com.smart.earthview.majisoft.model.Zone;
import com.smart.earthview.majisoft.model.Zones;
import com.smart.earthview.majisoft.sitesurvey.Survey;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.smart.earthview.majisoft.constant.SnackClass.setErrorSnackbar;
import static com.smart.earthview.majisoft.constant.SnackClass.setSnackBar;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener ,NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> zones,statusArray,mreaderArray,subzones;
    ArrayAdapter adapter,adapter2,mreaderAdapter,subadapter;
    Spinner zoneSpinner,mstatus,mreader,subzonespinner;
    Marker myMarker;
    ArrayList<CustomerClass> customerList;
    TextView txtaccno,txtcname,txtcdate;
    private GoogleMap googleMap;
    MapView mapView;
    Button btnsave,btnmsearch,btnpost,btnreset;
    EditText edtSearch,edtcreading;
    String meterSearch,currentReading,accNumber,mtrReader,mtrstatus,prdate,readerlocation,saveRecords;
    SharedPreferences mylocation;
    LatLng meterLocation;
    Constants constants;
   CoordinatorLayout relativemap;
   MeterReading meterReading;
    private static final int REQUEST_LOCATION_PERMISSION_CODE = 101;
    private GoogleApiClient googleApiClient;
    final static String ORDERS_URL = "http://olsen.joslabs.co.ke/mobileapp/getmyorders.php";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // mapView = (MapView) findViewById(map);//uncomment earlier version
        mapView = findViewById(R.id.map);
        mylocation = getApplicationContext().getSharedPreferences("mylocation", 0);
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
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            googleApiClient.connect();
            txtaccno = findViewById(R.id.txtaccno);
            txtcname = findViewById(R.id.txtcname);
            txtcdate = findViewById(R.id.txtcdate);
            edtSearch = findViewById(R.id.editTextsearch);
            edtcreading = findViewById(R.id.editcreading);
            zoneSpinner = findViewById(R.id.zonespinner);
            relativemap = findViewById(R.id.relativemap);
            btnreset = findViewById(R.id.btnreset);
        subzonespinner=findViewById(R.id.subzonespinner);
            //  final MajiRepository majiRepository=new MajiRepository(getApplicationContext());
            mstatus = findViewById(R.id.mstatus);
            mreader = findViewById(R.id.mreader);
            btnpost = findViewById(R.id.btnpost);
            constants = new Constants();
            zones = new ArrayList<>();
            zones.add("EASTERN");
            zones.add("NGIINI");
            zones.add("TOWN");
            zones.add("KALUNDU");
            zones.add("SWAHILI");
            zones.add("KWA NGINDU");
            zones.add("SITE");
            zones.add("MULANGO");
            zones.add("KAVISUNI");
            zones.add("MAJENGO");
            zones.add("KILONZO");
            zones.add("SYONGILA");

            adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, zones);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            zoneSpinner.setAdapter(adapter);
            subzones=new ArrayList<>();
            subzones.add("MWEMA");
            subzones.add("SALIMA");
            subzones.add("MATUNDA");
            subzones.add("ZALYNE");
            subadapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,subzones);
            subadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subzonespinner.setAdapter(subadapter);
            statusArray = new ArrayList<>();
            statusArray.add("Actual");
            statusArray.add("Gate closed");
            statusArray.add("Meter damaged");
            statusArray.add("Dogs");
            statusArray.add("No reading options");
            adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, statusArray);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mstatus.setAdapter(adapter2);
            mstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mtrstatus = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            mreaderArray = new ArrayList<>();
            mreaderArray.add("MAXWELL MUTINDA");
            mreaderArray.add("DENNIS IRERI");
            mreaderArray.add("ALEX MUMO");
            mreaderArray.add("KYALO NZUNGU");
            mreaderArray.add("JANE WASHEKE");
            mreaderArray.add("LYDIA ORANGA");
            mreaderArray.add("AGNES KAVENGI");
            mreaderArray.add("JOHNSON MUSEMBI");
            mreaderAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mreaderArray);
            mreaderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mreader.setAdapter(mreaderAdapter);
            mreader.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mtrReader = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //storing arraylist customer
            customerList = new ArrayList<>();
            customerList.add(new CustomerClass("0001", "00100", "Mulango", "12/12/2017", "Eastern", "-1.381811,38.002383"));
            customerList.add(new CustomerClass("0002", "00200", "Kavisuni", "12/12/2017", "Ngiini", "-1.366263,38.003794"));
            customerList.add(new CustomerClass("0003", "00300", "Majengo", "12/08/2018", "Town", "-1.384612,38.010665"));
            customerList.add(new CustomerClass("0004", "00400", "Kilonzo", "12/08/2018", "Kalundu", "-1.385298,38.007618"));
            customerList.add(new CustomerClass("0005", "00500", "Syiongila", "12/08/2018", "Swahili", "-1.372623,38.008870"));
            //PopulateData();
            //PopulateCustomers();//TODO uncomment this two
            btnmsearch = findViewById(R.id.btnsearch);
            btnsave = findViewById(R.id.btnregister);
            btnmsearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    meterSearch = edtSearch.getText().toString();
                    for (int x = 0; x < customerList.size(); x++) {
                        if (customerList.get(x).meter_no.toString().contains(meterSearch)) {
                            txtaccno.setText(customerList.get(x).acc_no);
                            txtcname.setText(customerList.get(x).userName);
                            txtcdate.setText(customerList.get(x).prDate);
                            String[] latslong = customerList.get(x).meter_location.split(",");
                            Log.e("coods", latslong[0] + "and longs\n" + latslong[1]);
                            String lats = latslong[0];
                            float lat = Float.valueOf(latslong[0]);
                            float llong = Float.valueOf(latslong[1]);
                            LatLng petrol = new LatLng(lat, llong);
                            meterLocation = petrol;
                            googleMap.clear();
                            myMarker = googleMap.addMarker(new MarkerOptions().position(petrol).title(customerList.get(x).userName).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maphere)));
                            //myMarker=googleMap.addMarker(new MarkerOptions().position(petrol).title("Previous position").snippet("@joslas.co.ke").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maphere)));
                            myMarker.showInfoWindow();
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(petrol).zoom(15).build();
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
                    if (inputsValidated()) {
                        //TODO UNCOMMENT THIS IN PRODUCTION
                        String mlocation = mylocation.getString("location", null);
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        prdate = format.format(today);

                        if (mlocation == null) {
                            readerlocation = "-1.198501943525175, 36.90164655447006";
                            showDialog("Open Location", "Please turn  your location on to get meter location update or save  offline  ");

                        } else {
                            String[] latslong = mlocation.split(",");
                            Log.e("coods", latslong[0] + "and longs\t" + latslong[1]);
                            String lats = latslong[0];
                            float lat = Float.valueOf(latslong[0]);
                            float llong = Float.valueOf(latslong[1]);
                            LatLng mineLocation = new LatLng(lat, llong);
                            readerlocation = mlocation;
                            Log.e("location", mineLocation + "");
                            LatLng check1 = new LatLng(-1.198501943525175, 36.90164655447006);
                            LatLng center2 = new LatLng(-1.1651393448365974, 36.824156530201435);

                            if (checkthis(meterLocation, mineLocation)) {//TODO get minelocation

                                saveReading();
                            } else {
                                //Toast.makeText(this, "checkthis false", Toast.LENGTH_SHORT).show();
                                showDialog("Save Reading", "Your current location and meter location do not match, ensure your are in meter location or click advance to add more details");

                            }
                        }
                    }
                }
            });

            btnpost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPostDialog("Confirm posting ", "please confirm you want to post offline survey data, once data is posted it will be cleared from local storage");

                }
            });

            btnreset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {


                            //was return false
                            return false;

                        }
                    });

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Intent meterLocation = new Intent(getApplicationContext(), MeterLocation.class);
                            startActivity(meterLocation);
                        }
                    });
                    googleMap = mMap;
                    //String initial="-1.385298,38.007618";
                    String[] latslong = customerList.get(1).meter_location.split(",");
                    Log.e("coods", latslong[0] + "and longs\n" + latslong[1]);
                    String lats = latslong[0];
                    float lat = Float.valueOf(latslong[0]);
                    float llong = Float.valueOf(latslong[1]);
                    LatLng petrol = new LatLng(lat, llong);
                    //LatLng petrol = new LatLng(-1.2179869, 36.8902669);
                    //googleMap.addMarker(new MarkerOptions().position(petrol).title(customerList.get(1).userName).snippet("Zone EPZ 4545,Location: Opposite Mwas shop +254711368518").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maphere)));
                    //myMarker=googleMap.addMarker(new MarkerOptions().position(petrol).title("Previous position").snippet("@joslas.co.ke").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maphere)));

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(petrol).zoom(15).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                /*mMap.addCircle(new CircleOptions()
                        .center(petrol)
                        .radius(250)
                        .strokeWidth(0f)
                        .fillColor(0x1100CCFF) //this is a half transparent blue, change "88" for the transparency
                        .strokeColor(R.color.colorPrimaryDark)
                        .strokeWidth(3)
                        );*/


                }
            });


      /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/

    }



    private void PopulateCustomers() {
        final MajiRepository majiRepository=new MajiRepository(getApplicationContext());
        try {
            majiRepository.getCustomers().observe(MainActivity.this, new Observer<List<CustomerClass>>() {
                @Override
                public void onChanged(@Nullable List<CustomerClass> customerClasses) {

                    for(CustomerClass customerClass : customerClasses){
                        customerList.add(customerClass);

                        Log.e("customerx",customerClass.getUserName());
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveReading(){
    currentReading=edtcreading.getText().toString().trim();
    accNumber=txtaccno.getText().toString().trim();
    Log.e("addrecord","step0");
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.GETSTATUS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Log.e("addrecord","step1");
    //Defining retrofit api service
    ApiService service = retrofit.create(ApiService.class);
    Log.e("addrecord","step2");
    final ProgressDialog loading = ProgressDialog.show(MainActivity.this, "Saving...", "Please wait...", false, false);

    //Defining the user object as we need to pass it with the call

    StatusClass user = new StatusClass("4",accNumber,currentReading,mtrReader,mtrstatus,prdate+"",readerlocation,"1");

    Call<NewRecordResults> call = service.createUser(
            user.getEntryId(),
            user.getAccNumber(),
            user.getCurrentReading(),
            user.getMtrReader(),
            user.getMtrStatus(),
            user.getRdate(),
            user.getLocation(),
            user.getActiveStatus()

    );
    Log.e("addrecord","step3");
    call.enqueue(new Callback<NewRecordResults>(){

        @Override
        public void onResponse(Call<NewRecordResults> call, retrofit2.Response<NewRecordResults> response) {
            loading.dismiss();
            Log.e("addrecord",response.body().toString());
            Toast.makeText(MainActivity.this, response+"", Toast.LENGTH_SHORT).show();
            Log.e("tags",response.body().getMessage());
            setSnackBar(relativemap,response.body().getMessage());
        }

        @Override
        public void onFailure(Call<NewRecordResults> call, Throwable t) {
            loading.dismiss();
            setErrorSnackbar(relativemap, "Unable to connect to Internet kindly check your internet connection ");

        }
    });


}


    private void startLocationMonitor() {
        Log.e(TAG, "start location monitor");
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(2000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    SharedPreferences.Editor editor=mylocation.edit();
                    editor.putString("location",location.getLatitude()+","+ location.getLongitude());
                    editor.apply();

                   MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(location.getLatitude(), location.getLongitude()));
                    markerOptions.title("Current Location");
                    //currentLocationMarker = googleMap.addMarker(markerOptions);
                    Log.e(TAG, "Location Change Lat Lng " + location.getLatitude() + " " + location.getLongitude());
                }
            });
        } catch (SecurityException e) {
            Log.e(TAG, e.getMessage());
        }

    }
    private void PopulateData() {

        final ProgressDialog loading = ProgressDialog.show(MainActivity.this, "Fetching...", "Please wait...", false, false);
        Call<MainSeedData>call=RetrofitClient.getRestClient().getSeedMain();
call.enqueue(new Callback<MainSeedData>() {
    @Override
    public void onResponse(Call<MainSeedData> call, retrofit2.Response<MainSeedData> response) {
        loading.dismiss();
        SeedDbMethod(response.body().getZoneList(),response.body().customerList);
        Log.e("custx",response.body().getError()+response.body().getZoneList().get(0).getZonename()+""+response.body().customerList.get(0).userName);
    }

    @Override
    public void onFailure(Call<MainSeedData> call, Throwable t) {
        loading.dismiss();
        setErrorSnackbar(relativemap,"Database configuration failed please try later and ensure there is internet connection ");
        Log.e("custx",t.getMessage());

    }
});
    }

    private void SeedDbMethod(ArrayList<Zone> zoneList, ArrayList<CustomerClass> customerList) {
        final MajiRepository majiRepository=new MajiRepository(getApplicationContext());
        for(Zone zone :zoneList){
            majiRepository.insertZone(zone);
        }
        for(CustomerClass customerClass : customerList){
            majiRepository.insertCustomer(customerClass);
        }
        setSnackBar(relativemap,"DB was successfully seeded, Customers: "+customerList.size()+" zones:"+zoneList.size());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //MenuItem seedDB=menu.findItem(R.id.seeddb);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.seeddb) {
            PopulateData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent ints=new Intent(getApplicationContext(),MeterLocation.class);
            startActivity(ints);

        } else if (id == R.id.nav_slideshow) {
            Intent ints=new Intent(getApplicationContext(),MeterStatus.class);
            startActivity(ints);


        } else if (id == R.id.nav_reconnect) {
            Intent ints=new Intent(getApplicationContext(),ReconnectionActivity.class);
            startActivity(ints);

        } else if (id == R.id.nav_disconnect) {
            Intent ints=new Intent(getApplicationContext(),DisconnectionActivity.class);
            startActivity(ints);

        } else if (id == R.id.nav_survey) {
            Intent ints=new Intent(getApplicationContext(), Survey.class);
            startActivity(ints);

        } else if (id == R.id.nav_analytics) {
            Intent ints=new Intent(getApplicationContext(),AnalyticsActivity.class);
            startActivity(ints);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showDialog(String title, String messaged) {
        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
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
                saveOffline();

            }
        });
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                saveReading();



            }
        });
        dialog.setIcon(R.drawable.ic_tick);
        dialog.show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("testTag", "Google Api Client Connected");


        startLocationMonitor();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "Google Connection Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(TAG, "Connection Failed:" + connectionResult.getErrorMessage());
    }
    private boolean arePointsNear(LatLng checkPoint, LatLng centerPoint, int km) {
        int ky = 40000 / 360;
        double kx = Math.cos(Math.PI * centerPoint.latitude / 180.0) * ky;
        double dx = Math.abs(centerPoint.longitude - checkPoint.longitude) * kx;
        double dy = Math.abs(centerPoint.latitude - checkPoint.latitude) * ky;
        return Math.sqrt(dx * dx + dy * dy) <= km;
    }
    private boolean checkthis(LatLng center, LatLng test)
    {
        float[] results = new float[1];
        Location.distanceBetween(center.latitude, center.longitude, test.latitude, test.longitude, results);
        float distanceInMeters = results[0];
        boolean isWithin10km = distanceInMeters < 200;
        return isWithin10km;
    }

public void postOffline(MeterReading meterReading, final String saveRecords){
    final ProgressDialog loading = ProgressDialog.show(MainActivity.this, "Posting...", "Please wait..."+saveRecords, false, false);


    Call<NewRecordResults> call = RetrofitClient.getRestClient().createUser(
           meterReading.getEntryId()+"",
            meterReading.getAccNumber(),
            meterReading.getCurrentReading(),
            meterReading.getMtrReader(),
            meterReading.getMtrStatus(),
            meterReading.getRdate(),
            meterReading.getLocation(),
            meterReading.getActiveStatus()

    );
    Log.e("addrecord","step3");
    call.enqueue(new Callback<NewRecordResults>(){

        @Override
        public void onResponse(Call<NewRecordResults> call, retrofit2.Response<NewRecordResults> response) {
            loading.dismiss();

            setSnackBar(relativemap,"Readings were successfully posted  "+saveRecords);
        }

        @Override
        public void onFailure(Call<NewRecordResults> call, Throwable t) {
            loading.dismiss();
            setErrorSnackbar(relativemap, "Unable to connect to Internet kindly check your internet connection ");

        }
    });
}

public void saveOffline(){

    final MajiRepository majiRepository=new MajiRepository(getApplicationContext());
    currentReading=edtcreading.getText().toString().trim();
    accNumber=txtaccno.getText().toString().trim();
    Random random=new Random();
    meterReading=new MeterReading(random.nextInt(),accNumber,currentReading,mtrReader,mtrstatus,prdate+"",readerlocation,"1");
    majiRepository.insertMeterReading(meterReading);
    setSnackBar(relativemap,"meter readings saved successfully");
}
    private void postOfflineData() {

        final MajiRepository majiRepository=new MajiRepository(getApplicationContext());
        majiRepository.getMeterReading().observe(MainActivity.this, new Observer<List<MeterReading>>() {
            int p=1;
            @Override
            public void onChanged(@Nullable List<MeterReading> meterReadings) {
                if (meterReadings.size() >= 1) {
                    // for (MeterReading meterReading: meterReadings){
                    for (int x = 0; x < meterReadings.size(); x++) {
                        saveRecords = p + "/" + meterReadings.size();
                        postOffline(meterReadings.get(x), saveRecords);
                        Log.e("svyrecord", saveRecords);
                        Log.e("mxread", meterReading.getAccNumber() + "\t" + meterReading.getMtrReader() + "\t" + meterReadings.size() + p);
                        p++;

                    }
                    if(p-1==meterReadings.size()){
                        final MajiRepository majiRepository=new MajiRepository(getApplicationContext());
                        majiRepository.deleteOfflineMeterReadings();
                        p=0;
                        Log.e("svydel","deletes");

                    }
                } else {
                    setErrorSnackbar(relativemap, "no offline survey data available for posting");
                }
            }
        });
    }
    private void showPostDialog(String title, String messaged) {
        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
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

    private boolean inputsValidated() {
        if(edtcreading.getText().toString().equals("")  ){
            edtcreading.setError("Please enter correct readings");
            return  false;
        }
        if(txtaccno.getText().toString().trim().equals("")||edtSearch.getText().toString().equals("")){
            setErrorSnackbar(relativemap,"Please search meter No for account before saving readings");
            return false;
        }
        return  true;
    }

    }
