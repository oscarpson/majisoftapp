package com.smart.earthview.majisoft.geofence;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceRegistrationService extends IntentService {

    private static final String TAG = "GeoIntentService";

    public GeofenceRegistrationService() {
        super(TAG);
        Log.e("geosrvc1","In Service geofence");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        Log.e("geosrvc","In Service geofence");
        if (geofencingEvent.hasError()) {
            Log.e(TAG, "GeofencingEvent error " + geofencingEvent.getErrorCode());
        } else {
            int transaction = geofencingEvent.getGeofenceTransition();
            List<Geofence> geofences = geofencingEvent.getTriggeringGeofences();
            Geofence geofence = geofences.get(0);
            if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_ID_STAN_UNI)) {
                Log.e(TAG, "You are inside Stanford University");
            } else {
                Log.e(TAG, "You are outside Stanford University");
            }
        }
    }
}