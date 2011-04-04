/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.geolix.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * @author TeepH
 */
public class LogsPosition extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "logspositiondb";
    private static final String TABLE_NAME = "position";
    private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                "LATITUDE FLOAT, " +
                "LONGITUDE FLOAT, " +
                "ALTITUDE INT, " +
                "PRIO_TRANFERT TEXT, " +
                "HEURE_LOG DATE);";

    LogsPosition(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
