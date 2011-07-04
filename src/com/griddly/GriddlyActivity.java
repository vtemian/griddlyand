package com.griddly;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GriddlyActivity extends Activity {
	List<fi.foyt.foursquare.api.entities.Location> venues;
	Button loginbtn;
	EditText username;
	EditText password;
	String local_username = "";
	Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        String bestProvider;
        
        try {
			Socket requestSocket = new Socket("localhost", 8080);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        SharedPreferences settings = getSharedPreferences("preference", 0);
        boolean logged = settings.getBoolean("logged", false);
        if(logged){
        	Intent intent = new Intent(GriddlyActivity.this, Checkin.class);
         	startActivity(intent);
        }else{
        	context = getApplicationContext();

        	loginbtn = (Button) findViewById(R.id.Login);
	        username = (EditText) findViewById(R.id.Username);
	        password = (EditText) findViewById(R.id.Password);
	        
	        loginbtn.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	
	            	String uusername = username.getText().toString(); 
	            	String upassword = password.getText().toString();
	            	
	            	URL myURL;
					try {
						 myURL = new URL("http://192.168.2.111:8000/external-api/login/?username="+uusername+"&"+"password="+upassword);
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
			             
			             if(result.indexOf("login")>=0){
			            	 SharedPreferences settings = getSharedPreferences("preference", 0);
			                 SharedPreferences.Editor editor = settings.edit();
			                 editor.putBoolean("logged", true);
			                 editor.commit();
			                 
			            	Intent intent = new Intent(GriddlyActivity.this, Checkin.class);
			             	startActivity(intent);
			             }else{
			            	 Toast.makeText(context, "User doesn't exist!", Toast.LENGTH_LONG).show();
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
        }
    }
}
