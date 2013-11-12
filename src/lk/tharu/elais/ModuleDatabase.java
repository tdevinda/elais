package lk.tharu.elais;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ModuleDatabase {
	
	protected static final String DB_NAME = "elaisdb"; 
	protected static final String TABLE_STATIONS = "stations";
	private static final String col_stationID = "sid";
	private static final String col_stationName = "stname";
	private static final String col_stationLatitute = "stlati";
	private static final String col_stationLongitude = "stlong";
	private static final String col_stationAddress = "staddr";
	private static final String col_stationArea = "starea";
	private static final String col_fuelPetrol90 = "fuelp90";
	private static final String col_fuelPetrol95 = "fuelp95";
	private static final String col_fuelPetrolEuro = "fueleur";
	private static final String col_fuelDieselRegular = "fueldrg";
	private static final String col_fuelDieselSuper = "fueldsp";
	private static final String col_fuelLiquidGas = "fuellpg";
	private static final String col_serviceAir = "srvair";
	private static final String col_serviceMarket = "srvmkt";
	
	protected static final String TABLE_REFILLS = "refills";
	private static final String col_entryID = "eid";
	private static final String col_entrytime = "etime";
	private static final String col_entryLitres = "evolume";
	private static final String col_entryCardID = "ecid";
	private static final String col_entryTransID = "etrid";
	private static final String col_entryCost = "ecost";
	private static final String col_entryBalance = "ebal";
	private static final String col_entryStation = "estation";
	private static final String col_entryFuel = "etype";
	
	private Context context;
	
	public ModuleDatabase(Context c) {
		this.context = c;
	}
	
	
	
	class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
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
			
			db.execSQL("create table "+ TABLE_REFILLS + ") "+
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
