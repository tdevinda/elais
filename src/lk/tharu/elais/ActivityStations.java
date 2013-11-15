package lk.tharu.elais;

import com.lvp.ListViewPopulator;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class ActivityStations extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_stations);
		
		
		ListView listView = (ListView) findViewById(R.id.list_stations_stations);
		ModuleDatabase mdb = new ModuleDatabase(getApplicationContext());
		
		final ListViewPopulator lvp = new ListViewPopulator(getApplicationContext(), mdb.getDBHelper(), ModuleDatabase.TABLE_STATIONS);
		lvp.setListView(listView);
		lvp.setListViewEntryLayoutID(R.layout.entry_station);
		lvp.setMapping(R.id.text_entryStation_name, ModuleDatabase.col_stationName);
		lvp.setMapping(R.id.text_entryStation_area, ModuleDatabase.col_stationArea);
		
		
		final LocationManager locationManager = (LocationManager) 
				getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		
		
		Location dummyLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		double latlonDifference = 0.1;
		lvp.populate(
				ModuleDatabase.col_stationLatitute +" > ? and "+ ModuleDatabase.col_stationLatitute +" < ? and "+
				ModuleDatabase.col_stationLongitude +" > ? and "+ ModuleDatabase.col_stationLongitude +" < ?",

				new String[] {
					Double.toString(dummyLocation.getLatitude() - latlonDifference),
					Double.toString(dummyLocation.getLatitude() + latlonDifference),
					Double.toString(dummyLocation.getLongitude() - latlonDifference),
					Double.toString(dummyLocation.getLongitude() + latlonDifference),
				}
		);
		
		
		
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
				Log.v("elais", "new location");
				
				//TODO populate the stations here.
				int defaultStationRange = 10;
				long latlonDifference = defaultStationRange / 100;
				Location dummyLocation = new Location(location);
				dummyLocation.setLatitude(6.75);
				dummyLocation.setLongitude(79.89);
				
				lvp.populate(
						ModuleDatabase.col_stationLatitute +" > ? and "+ ModuleDatabase.col_stationLatitute +" < ? and "+
						ModuleDatabase.col_stationLongitude +" > ? and "+ ModuleDatabase.col_stationLongitude +" < ?",
						
						new String[] {
								Double.toString(dummyLocation.getLatitude() - latlonDifference),
								Double.toString(dummyLocation.getLatitude() + latlonDifference),
								Double.toString(dummyLocation.getLongitude() - latlonDifference),
								Double.toString(dummyLocation.getLongitude() + latlonDifference),
						});
				
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
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationUpdateListener); 
			Log.v("elais", "requested for network updates");
		}
		
		/*
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationUpdateListener);
		} */
	}
}
