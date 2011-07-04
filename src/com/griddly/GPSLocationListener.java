package com.griddly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class GPSLocationListener extends Activity implements LocationListener{
	public Context context;
	public Venues VenuesList = new Venues();
	public LinearLayout layout;
	
	public GPSLocationListener(Context lcontext, LinearLayout lLayout){
		context = lcontext;
		layout = lLayout;

	}
	 public void searchVenues(String ll) throws FoursquareApiException {
		    FoursquareApi foursquareApi = new FoursquareApi("2LMPRCIPRWUSR0AWLRL4M4SYRA5XG4XY2L4TJ2BM3GXAI5XJ",
		    		"EXBXRD5J4V4V2OIH0GZN5XROZKUDML4RKARB0WPMFQPFRBFU", null);
		    foursquareApi.setUseCallback(false);
		    Result<VenuesSearchResult> result = foursquareApi.venuesSearch(ll, 3000.0, null, null, null, 5, null, null, null, null, null);
		    
		    if (result.getMeta().getCode() == 200) {
		    	boolean data = false;	
		    	Venues venues = new Venues();
		    	
		    	for (CompactVenue venue : result.getResult().getVenues()) {
		    		venues.add(venue);
		    	}
		    	
		    	System.out.println("---------------Start List------------");
		    	for (CompactVenue venue : venues.getVenues()) {
		    		fi.foyt.foursquare.api.entities.Location loc =  venue.getLocation();
		    		Double lang = loc.getLat();
		    		Double lng = loc.getLng();
		    		addButton(venue.getName(), Double.toString(lang), Double.toString(lng));
		    		data = true;
		    		System.out.println("Distance: "+venue.getLocation().getDistance());
		    	}
		    	if(!data)
		    		Toast.makeText(context, "No results", Toast.LENGTH_LONG).show();
		    }else{
		    	Toast.makeText(context, "Crashed! Please reload!", Toast.LENGTH_LONG).show();
		    }  
		  }
	public void addButton(final String name, final String lang, final String lng){
		Button button = new Button(this.context);
		button.setText(name);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				URL myURL;
				try {
					SharedPreferences settings = getSharedPreferences("preference", 0);
		            String username = settings.getString("username", "|");
		              
					myURL = new URL("http://192.168.2.111:8000/external-api/checkin/?username="+username+"&checkin="+name+"&lang="+lang+"&lng="+lng);
					 
					 URLConnection myURLConnection = myURL.openConnection();
		             BufferedReader rd = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
		             StringBuffer sb = new StringBuffer();
		             String line;
		             while ((line = rd.readLine()) != null)
		             {
		            	 sb.append(line);
		             }
		             rd.close();
		             String result = sb.toString();
		             if(result.indexOf("ok")==-1){
		            	 Toast.makeText(context, "Error! John, be more carefull!", Toast.LENGTH_LONG);
		             }
		             
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	             
			}
		});
		
		this.layout.addView(button);
	}
	@Override
	public void onLocationChanged(Location loc)
	{
		layout.removeAllViews();
		Double lat = loc.getLatitude();
		Double lon = loc.getLongitude();
		try {
			searchVenues(lat.toString()+","+lon.toString());
		} catch (FoursquareApiException e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
		}
	}
	@Override
	public void onProviderDisabled(String provider)
	{
		Toast.makeText( context, "Gps Disabled",	Toast.LENGTH_SHORT ).show();
	}
	@Override
	public void onProviderEnabled(String provider)
	{
		Toast.makeText( context, "Gps Enabled",	Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		
	}
	
}
