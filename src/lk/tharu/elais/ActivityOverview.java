package lk.tharu.elais;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
				
				Intent showStationsListIntent = new Intent(ActivityOverview.this, ActivityStations.class);
				startActivity(showStationsListIntent);
			}
		});
		
		
		Button showHistory = (Button) findViewById(R.id.btn_overview_showHistory);
		showHistory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				ModuleDatabase mdb = new ModuleDatabase(getApplicationContext());
				mdb.deleteAllStations();
				mdb.addBogusStation("Felix Perera", "Horton Place", 6.911351, 79.86494);
				mdb.addBogusStation("SS Kotalawela", "Narahenpita", 6.911351, 79.86494);
				mdb.addBogusStation("Kottawa Lanka", "Kottawa", 6.840853, 79.965844);
				
				mdb.getNearbyStations(6.75, 79.89, 0.1);
				
			}
		});
		
	}
}
