package lk.tharu.elais;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.lvp.Calculation;
import com.lvp.Calculation.OPERAND;
import com.lvp.Element;
import com.lvp.Element.TYPE;
import com.lvp.ListViewPopulator;

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
		lvp.setMapping(0, ModuleDatabase.col_stationLatitute);
		lvp.setMapping(0, ModuleDatabase.col_stationLongitude);
		
		
		
		final LocationManager locationManager = (LocationManager) 
				getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		
		
		Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		//dummyLocation.setLatitude(6.75);
		//dummyLocation.setLongitude(79.89);
		
		
		
		lvp.setCalculation(getDistanceCalculation(lastKnownLocation, 0.1), R.id.text_entryStation_distance);
		
		double latlonDifference = 0.1;
		lvp.populate(
				ModuleDatabase.col_stationLatitute +" > ? and "+ ModuleDatabase.col_stationLatitute +" < ? and "+
				ModuleDatabase.col_stationLongitude +" > ? and "+ ModuleDatabase.col_stationLongitude +" < ?",

				new String[] {
					Double.toString(lastKnownLocation.getLatitude() - latlonDifference),
					Double.toString(lastKnownLocation.getLatitude() + latlonDifference),
					Double.toString(lastKnownLocation.getLongitude() - latlonDifference),
					Double.toString(lastKnownLocation.getLongitude() + latlonDifference)
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
				double defaultStationRange = 10.0;
				double latlonDifference = defaultStationRange / 100;
				
				lvp.populate(
						ModuleDatabase.col_stationLatitute +" > ? and "+ ModuleDatabase.col_stationLatitute +" < ? and "+
						ModuleDatabase.col_stationLongitude +" > ? and "+ ModuleDatabase.col_stationLongitude +" < ?",
						
						new String[] {
								Double.toString(location.getLatitude() - latlonDifference),
								Double.toString(location.getLatitude() + latlonDifference),
								Double.toString(location.getLongitude() - latlonDifference),
								Double.toString(location.getLongitude() + latlonDifference)
						}
						);
				
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
	
	
	private Calculation getDistanceCalculation(Location location, double range) {

		
		//x = (dbLat - currentLat) ^ 2 
		Calculation latSq = new Calculation(
				new Element(new Calculation(
						new Element(ModuleDatabase.col_stationLatitute, TYPE.DB_COLUMN),
						OPERAND.SUBTRACT,
						new Element(location.getLatitude(), TYPE.NUMBER)
						), TYPE.CALCULATION), 
						OPERAND.POWER, 
						new Element(2, TYPE.NUMBER)
				);

		//y = (dbLon - currentLon) ^ 2
		Calculation lonSq = new Calculation(
				new Element(new Calculation(
						new Element(ModuleDatabase.col_stationLongitude, TYPE.DB_COLUMN),
						OPERAND.SUBTRACT,
						new Element(location.getLongitude(), TYPE.NUMBER)
						), TYPE.CALCULATION), 
						OPERAND.POWER, 
						new Element(2, TYPE.NUMBER)
				);
		// x + y
		Calculation added = new Calculation(
				new Element(latSq, TYPE.CALCULATION),
				OPERAND.ADD,
				new Element(lonSq, TYPE.CALCULATION)
				);

		//z=  sqrt(x + y)
		Calculation latondiff = new Calculation(
				new Element(added, TYPE.CALCULATION),
				OPERAND.ROOT,
				new Element(2, TYPE.NUMBER)
				);

		// round (z)
		Calculation roundedDifference = new Calculation(
				new Element(new Calculation(
						new Element(latondiff, TYPE.CALCULATION),
						OPERAND.MULTIPLY,
						new Element(100, TYPE.NUMBER)
						), TYPE.CALCULATION), 
						OPERAND.ROUND, 
						new Element(0, TYPE.NUMBER));
		//value = z + " km"
		Calculation displayed = new Calculation(
				new Element(roundedDifference, TYPE.CALCULATION),
				OPERAND.CONCAT,
				new Element(" km", TYPE.NUMBER)
				);

		return displayed;
	}
}
