package lk.tharu.elais;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityOverview extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_overview);
		
		
		Button searchStations = (Button) findViewById(R.id.btn_overview_searchStations);
		searchStations.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				final LocationManager locationManager = (LocationManager) 
						getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
				
				LocationListener locationUpdateListener = new LocationListener() {
					
					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onLocationChanged(Location location) {
						
						//TODO populate the stations here.
						
						Log.v("elais", "new location from " + location.getProvider() + 
								" lat="+ location.getLatitude() + " lon="+ location.getLongitude() +
								" accuracy="+ location.getAccuracy());
						
						if(location.getAccuracy() < 100) {
							locationManager.removeUpdates(this);
							Log.v("elais", "got accurate measurement");
						}
						
					}
				};
				
				if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
					//if the network based location is available, start using it to populate the data
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 500, locationUpdateListener); 
				}
				
				if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 100, locationUpdateListener);
				}
			}
		});
		
	}
}
