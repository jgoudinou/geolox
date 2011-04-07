/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.geolix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 *
 * @author TeepH
 */
public class LogsPosition extends SQLiteOpenHelper implements LocationListener {
  private static final int DATABASE_VERSION = 2;
  private static final String DATABASE_NAME = "logsposition";
  private static final String TABLE_NAME = "position";
  private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
            "LATITUDE FLOAT, " +
            "LONGITUDE FLOAT, " +
            "ALTITUDE FLOAT, " +
            "VITESSE FLOAT, " +
            "PRIO_TRANFERT TEXT, " +
            "HEURE_LOG TEXT);";

  private Context myContext;

  LogsPosition(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.myContext=context;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(TABLE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void onLocationChanged(Location loc) {
    //String.valueOf(location.getLatitude())
    ContentValues lig = new ContentValues();
    lig.put("LATITUDE",loc.getLatitude());
    lig.put("LONGITUDE",loc.getLongitude());
    lig.put("ALTITUDE",loc.getAltitude());
    lig.put("VITESSE",loc.getSpeed());
    lig.put("PRIO_TRANFERT","GPS");
    lig.put("HEURE_LOG",Long.toString(loc.getTime()));
    this.getWritableDatabase().insert(TABLE_NAME, DATABASE_NAME, lig);

    Log.e("DEBUG","J'ai fait un insert");
    
    //String[] g = {"ff","ff","ff","ff"};
    Cursor csr = this.getReadableDatabase().query(TABLE_NAME,null, null, null, null, null, null);
    Log.e("DEBUG","Nombre de positions : "+String.valueOf(csr.getCount()));
    
    ((GeopositionActivity)this.myContext).setAltitude(String.valueOf(csr.getCount()));

    csr.close();
    csr.deactivate();


  }

  public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    Log.e("DEBUG",arg0);
  }

  public void onProviderEnabled(String arg0) {
    //TODO Peut etre gerer ici la permutation sur la localiation GSM (inverse)
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void onProviderDisabled(String arg0) {
    //TODO Peut etre gerer ici la permutation sur la localiation GSM
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
