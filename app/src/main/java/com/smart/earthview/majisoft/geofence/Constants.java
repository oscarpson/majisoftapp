package com.smart.earthview.majisoft.geofence;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class Constants {
    public static final String GEOFENCE_ID_STAN_UNI = "STAN_UNI";
    public static final float GEOFENCE_RADIUS_IN_METERS = 100;

    /**
     * Map for storing information about stanford university in the Stanford.
     */
    public static final HashMap<String, LatLng> AREA_LANDMARKS = new HashMap<String, LatLng>();

    static {
        // stanford university.
        AREA_LANDMARKS.put(GEOFENCE_ID_STAN_UNI, new LatLng( -1.1651393448365974	,36.824156530201435));
    }
}
