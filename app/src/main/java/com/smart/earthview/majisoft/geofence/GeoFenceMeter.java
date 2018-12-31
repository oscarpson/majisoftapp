package com.smart.earthview.majisoft.geofence;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smart.earthview.majisoft.R;

import com.google.android.gms.location.LocationListener;


public class GeoFenceMeter extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener ,OnMapReadyCallback {

    private GoogleMap mMap;
    private GeofencingRequest geofencingRequest;
    private GoogleApiClient googleApiClient;
    private GeofencingClient mGeofencingClient;

    private boolean isMonitoring = false;

    private MarkerOptions markerOptions;

    private Marker currentLocationMarker;
    private PendingIntent pendingIntent;
    private static final String TAG = "MainActivity";
    private static final int REQUEST_LOCATION_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_fence_meter);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mGeofencingClient = LocationServices.getGeofencingClient(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        googleApiClient.connect();
        Toast.makeText(getApplicationContext(), "connected", Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION_CODE);
        }


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

                    if (currentLocationMarker != null) {
                        currentLocationMarker.remove();
                    }
                    markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(location.getLatitude(), location.getLongitude()));
                    markerOptions.title("Current Location");
                    currentLocationMarker = mMap.addMarker(markerOptions);
                    Log.e(TAG, "Location Change Lat Lng " + location.getLatitude() + " " + location.getLongitude());
                }
            });
        } catch (SecurityException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    private void startGeofencing() {
        Log.e(TAG, "Start geofencing monitoring call");
        pendingIntent = getGeofencePendingIntent();
        geofencingRequest = new GeofencingRequest.Builder()
                .setInitialTrigger(Geofence.GEOFENCE_TRANSITION_ENTER)
                .addGeofence(getGeofence())
                .build();

        if (!googleApiClient.isConnected()) {
            Log.e(TAG, "Google API client not connected");
        } else {
            try {
                LocationServices.GeofencingApi.addGeofences(googleApiClient, geofencingRequest, pendingIntent).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.e(TAG, "Successfully Geofencing Connected");
                        } else {
                            Log.e(TAG, "Failed to add Geofencing " + status.getStatus());
                        }
                    }
                });
            } catch (SecurityException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        isMonitoring = true;
        invalidateOptionsMenu();
    }

    @NonNull
    private Geofence getGeofence() {
        LatLng latLng = Constants.AREA_LANDMARKS.get(Constants.GEOFENCE_ID_STAN_UNI);
        return new Geofence.Builder()
                .setRequestId(Constants.GEOFENCE_ID_STAN_UNI)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setCircularRegion(latLng.latitude, latLng.longitude, Constants.GEOFENCE_RADIUS_IN_METERS)
                .setNotificationResponsiveness(1000)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
    }

    private PendingIntent getGeofencePendingIntent() {
        if (pendingIntent != null) {
            return pendingIntent;
        }
        Intent intent = new Intent(this, GeofenceRegistrationService.class);
        Log.e("Georeg","Geo register");
        return PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        this.mMap = googleMap;
        LatLng latLng = Constants.AREA_LANDMARKS.get(Constants.GEOFENCE_ID_STAN_UNI);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Geofence location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f));

        googleMap.setMyLocationEnabled(true);

        Circle circle = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latLng.latitude, latLng.longitude))
                .radius(Constants.GEOFENCE_RADIUS_IN_METERS)
                .strokeColor(Color.RED)
                .strokeWidth(4f));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("testTag", "Google Api Client Connected");
        isMonitoring = true;
        startGeofencing();
        startLocationMonitor();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "Google Connection Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        isMonitoring = false;
        Log.e(TAG, "Connection Failed:" + connectionResult.getErrorMessage());
    }
}


