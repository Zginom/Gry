package com.example.daniel.gry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Daniel on 06.07.2017.
 */

public class DBAdapter {
    private static final String DEBUG_TAG = "SqLiteManager";
    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;

    private static final String DB_NAME ="bazaRekodrow.db";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "wyniki";

    public static final String KEY_ID = "id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String KEY_DESCRIPTION = "nazwa_gry";
    public static final String DESCRIPTION_OPTIONS = "TEXT NOT NULL";
    public static final String KEY_NUMBER = "punkty";
    public static final String NUMBER_OPTIONS = "INTEGER DEFAULT 0";

    private static final String DB_CREATE_TABLE =
            "CREATE TABLE " + DB_TABLE + "(" +
                    KEY_ID + " " + ID_OPTIONS + "," +
                    KEY_DESCRIPTION + " " + DESCRIPTION_OPTIONS + "," +
                    KEY_NUMBER + " " + NUMBER_OPTIONS + ");";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context , String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context,name,factory,version);
        }

        public void onCreate(SQLiteDatabase db){
            db.execSQL(DB_CREATE_TABLE);
            Log.d(DEBUG_TAG,"Database creating...");
            Log.d(DEBUG_TAG,"Table " + DB_TABLE + "ver." + DB_VERSION + " created");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Table " + DB_TABLE + " updated to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost");

            onCreate(db);
        }
    }

    public DBAdapter(Context context) {
        this.context = context;
    }

    public DBAdapter open() {
        dbHelper = new DatabaseHelper(context, DB_NAME , null, DB_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch ( SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public Long insertWynik(String description, int number) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_DESCRIPTION,description);
        newValues.put(KEY_NUMBER,number);
        Log.d("UWAGA DODDAJEMY","TROLOLO");
        return db.insert(DB_TABLE,null,newValues);
    }

    public Boolean usunWszystko(){
        return db.delete(DB_TABLE,null,null) > 0;
    }

    public Cursor dajWszystko()
    {
        String [] kolumny = {KEY_ID,KEY_DESCRIPTION,KEY_NUMBER};
        Cursor kursor = db.query(DB_TABLE,kolumny,null,null,null,null,null);
        return kursor;
    }

}