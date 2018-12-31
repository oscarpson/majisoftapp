package com.smart.earthview.majisoft.geofence;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.smart.earthview.majisoft.R;

public class MeterFence extends AppCompatActivity
        implements OnMapReadyCallback {

    private static final int LOC_PERM_REQ_CODE = 1;
    //meters
    private static final int GEOFENCE_RADIUS = 500;
    //in milli seconds
    private static final int GEOFENCE_EXPIRATION = 6000;

    private GoogleMap mMap;

    private GeofencingClient geofencingClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter_fence);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);

        geofencingClient = LocationServices.getGeofencingClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(15);

        showCurrentLocationOnMap();
        addLocationAlert(-1.1651393448365974	,36.824156530201435);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                addLocationAlert(latLng.latitude, latLng.longitude);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void showCurrentLocationOnMap() {
        if (isLocationAccessPermitted()) {
            requestLocationAccessPermission();
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }
    private boolean isLocationAccessPermitted(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            return false;
        }
    }
    private void requestLocationAccessPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOC_PERM_REQ_CODE);
    }

    @SuppressLint("MissingPermission")
    private void addLocationAlert(double lat, double lng){
        if (isLocationAccessPermitted()) {
            requestLocationAccessPermission();
        } else  {
            String key = ""+lat+"-"+lng;
            Geofence geofence = getGeofence(lat, lng, key);
            geofencingClient.addGeofences(getGeofencingRequest(geofence),
                    getGeofencePendingIntent())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MeterFence.this,
                                        "Location alter has been added",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MeterFence.this,
                                        "Location alter could not be added",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private void removeLocationAlert(){
        if (isLocationAccessPermitted()) {
            requestLocationAccessPermission();
        } else {
            geofencingClient.removeGeofences(getGeofencePendingIntent())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MeterFence.this,
                                        "Location alters have been removed",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MeterFence.this,
                                        "Location alters could not be removed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOC_PERM_REQ_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCurrentLocationOnMap();
                    Toast.makeText(MeterFence.this,
                            "Location access permission granted, you try " +
                                    "add or remove location allerts",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(this, LocationAlertIntentService.class);
        return PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private GeofencingRequest getGeofencingRequest(Geofence geofence) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL);
        builder.addGeofence(geofence);
        return builder.build();
    }

    private Geofence getGeofence(double lat, double lang, String key) {
        return new Geofence.Builder()
                .setRequestId(key)
                .setCircularRegion(lat, lang, GEOFENCE_RADIUS)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_DWELL)
                .setLoiteringDelay(10000)
                .build();
    }
    //TODO add menu and uncomment this
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.remove_loc_alert:
                removeLocationAlert();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    */
}