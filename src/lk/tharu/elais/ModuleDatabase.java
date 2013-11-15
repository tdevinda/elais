package lk.tharu.elais;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ModuleDatabase {
	
	protected static final String DB_NAME = "elaisdb"; 
	protected static final String TABLE_STATIONS = "stations";
	public static final String col_stationID = "sid";
	public static final String col_stationName = "stname";
	public static final String col_stationLatitute = "stlati";
	public static final String col_stationLongitude = "stlong";
	public static final String col_stationAddress = "staddr";
	public static final String col_stationArea = "starea";
	public static final String col_fuelPetrol90 = "fuelp90";
	public static final String col_fuelPetrol95 = "fuelp95";
	public static final String col_fuelPetrolEuro = "fueleur";
	public static final String col_fuelDieselRegular = "fueldrg";
	public static final String col_fuelDieselSuper = "fueldsp";
	public static final String col_fuelLiquidGas = "fuellpg";
	public static final String col_serviceAir = "srvair";
	public static final String col_serviceMarket = "srvmkt";
	
	protected static final String TABLE_REFILLS = "refills";
	public static final String col_entryID = "eid";
	public static final String col_entrytime = "etime";
	public static final String col_entryLitres = "evolume";
	public static final String col_entryCardID = "ecid";
	public static final String col_entryTransID = "etrid";
	public static final String col_entryCost = "ecost";
	public static final String col_entryBalance = "ebal";
	public static final String col_entryStation = "estation";
	public static final String col_entryFuel = "etype";
	
	private Context context;
	
	public ModuleDatabase(Context c) {
		this.context = c;
	}
	
	
	public void deleteAllStations() {
		DBHelper helper = new DBHelper();
		SQLiteDatabase db = helper.getWritableDatabase();
		
		db.delete(TABLE_STATIONS, null, null);
		db.close(); helper.close();
	}
	
	public void addBogusStation(String name, String area, String lat, String lon) {
		DBHelper helper = new DBHelper();
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(col_stationName, name);
		cv.put(col_stationArea, area);
		cv.put(col_stationLatitute, lat);
		cv.put(col_stationLongitude, lon);
		
		db.insert(TABLE_STATIONS, null, cv);
		db.close();
		helper.close();
		cv = null;
		
	}
	
	
	public DBHelper getDBHelper() {
		DBHelper helper = new DBHelper();
		return helper;
	}
	
	
	class DBHelper extends SQLiteOpenHelper {

		public DBHelper() {
			super(context, DB_NAME, null, 1);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table "+ TABLE_STATIONS + "(" +
					col_stationID + " integer primary key autoincrement," +
					col_stationName + " text," +
					col_stationAddress + " text," +
					col_stationArea + " text," +
					col_stationLatitute + " text," +
					col_stationLongitude + " text," +
					col_fuelPetrol90 + " integer," +
					col_fuelPetrol95 + " integer," +
					col_fuelPetrolEuro + " integer," +
					col_fuelDieselRegular + " integer," +
					col_fuelDieselSuper + " integer," +
					col_fuelLiquidGas + " integer," +
					col_serviceAir + " integer," +
					col_serviceMarket + " integer)"
					);
			
			db.execSQL("create table "+ TABLE_REFILLS + "( "+
					col_entryID +" integer primary key autoincrement,"+
					col_entrytime +" text,"+
					col_entryLitres +" text,"+
					col_entryCardID + " text,"+
					col_entryTransID +" text,"+
					col_entryCost + " text,"+
					col_entryBalance + " text,"+
					col_entryStation + " text,"+
					col_entryFuel + " text)"
					);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			
		}
		
	}
}
