package com.griddly;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Checkin extends Activity {
	List<fi.foyt.foursquare.api.entities.Location> venues;
	Button loginbtn;
	EditText username;
	EditText password;
	String local_username = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin);
        String bestProvider;
        LinearLayout ll = (LinearLayout) findViewById(R.id.LLayout);
        LocationManager locManager =	(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = locManager.getAllProviders();
		Criteria criteria = new Criteria();
		bestProvider = locManager.getBestProvider(criteria, false);
		Location location = locManager.getLastKnownLocation(bestProvider);
		LocationListener mlocListener = new GPSLocationListener(getApplicationContext(), ll);
        locManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);
        mlocListener.onLocationChanged(location);
    }

}
