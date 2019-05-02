package com.smart.earthview.majisoft;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MeterLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleMap googleMap;
    MapView mapView;
    ArrayList<String> zones;
    ArrayAdapter adapter;
    Spinner zoneSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapView=findViewById(R.id.map);

        mapView.onCreate(savedInstanceState);

        mapView.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        zoneSpinner=findViewById(R.id.zonespinner);
        zones=new ArrayList<>();
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


        adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,zones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zoneSpinner.setAdapter(adapter);

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
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Log.e("geoLats",latLng.latitude+",\t"+latLng.longitude);
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
                LatLng petrol = new LatLng(-1.2179869, 36.8902669);
                googleMap.addMarker(new MarkerOptions().position(petrol).title("Kunda Kindu").snippet("Zone EPZ 4545,Location: Opposite Mwas shop +254711368518").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maphere)));
                //myMarker=googleMap.addMarker(new MarkerOptions().position(petrol).title("Previous position").snippet("@joslas.co.ke").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maphere)));

                CameraPosition cameraPosition=new CameraPosition.Builder().target(petrol).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                final LatLng[] covers = new LatLng[]{
                        new LatLng(-1.198501943525175,36.90164655447006),
                        new LatLng(-1.199922197330275,36.90031751990318),
                        new LatLng(-1.200915402467943,36.898950934410095),
                        new LatLng(-1.2021301759964402,36.897456273436546),
                        new LatLng(-1.2164074115325598,	36.8850078061223),
                        new LatLng(-1.2730771918082914,36.900918669998646),
                        new LatLng(-1.2828743952053982,36.9014598056674),
                        new LatLng(-1.2162736665014886,	36.88115984201431),
                        new LatLng(-1.302754393241491,36.90216757357121)
                };
                 for(int x = 0;x< covers.length-1; x++)
                 {
                     googleMap.addMarker(new MarkerOptions().position(covers[x]).title("Kunda Kindu").snippet("Zone EPZ 4545,Location: Opposite Mwas shop +254711368518").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maphere)));

                 }






            }
        });
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
