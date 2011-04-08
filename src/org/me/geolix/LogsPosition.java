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
import java.util.logging.Handler;

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


  public static Thread performOnBackgroundThread(final Runnable runnable) {
    final Thread t = new Thread() {
        @Override
        public void run() {
            try {
                runnable.run();
            } finally {

            }
        }
    };
    t.start();
    return t;
   }

  public Thread HttpPush(final Handler handler, final Context context) {
    Log.d("DEBUG", "Push 2");
      final Runnable runnable = new Runnable() {
          public void run() {
            Log.e("DEBUG", "Push 3");
            NetworkUtilities.pushPositions("TeepH", serializePositions(10));
            Log.e("DEBUG", "Push 5");
          }
      };
      // run on background thread.
      return LogsPosition.performOnBackgroundThread(runnable);
  }

  protected synchronized String serializePositions(int nb){
    Log.e("DEBUG", "Push 4");
    Cursor csr = this.getReadableDatabase().query(TABLE_NAME,null, null, null, null, null, null, String.valueOf(nb));
    Log.e("DEBUG","Nombre de positions : "+String.valueOf(csr.getCount()));

    int latitude=csr.getColumnIndex("LATITUDE");
    int longitude=csr.getColumnIndex("LONGITUDE");
    int altitude=csr.getColumnIndex("ALTITUDE");
    int vitesse=csr.getColumnIndex("VITESSE");
    int heurelog=csr.getColumnIndex("HEURE_LOG");

    String data="TST";
    while(csr.moveToNext()){
      data.concat(String.valueOf(csr.getFloat(latitude)).concat(";"));
      data.concat(String.valueOf(csr.getFloat(longitude)).concat(";"));
      data.concat(String.valueOf(csr.getFloat(altitude)).concat(";"));
      data.concat(String.valueOf(csr.getFloat(vitesse)).concat(";"));
      data.concat(String.valueOf(csr.getFloat(heurelog)).concat(";"));
      data.concat("|");
    }

    csr.close();
    csr.deactivate();
    return data;
  }

  public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    Log.e("DEBUG","Le status de "+arg0+" a changé");
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
