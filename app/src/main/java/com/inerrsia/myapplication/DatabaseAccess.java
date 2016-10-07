package com.inerrsia.myapplication;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public String getsyn( String a ) {
        try
        {
            Cursor cursor = this.database.rawQuery("SELECT * From Words WHERE `Word` = '"+a+"'", null);
            cursor.moveToFirst();
            int pos = cursor.getColumnIndex("Synonyms");
            String val = cursor.getString(pos);
            if (val == null)
                return "null";
            else
                return cursor.getString(pos);
        }
        catch(Exception e)
        {
            return "0";
        }
    }
    public String getmean( String m )
    {
        try
        {
            Cursor cursor = this.database.rawQuery("SELECT * From Words WHERE `Word` = '"+m.toLowerCase()+"'", null);
            cursor.moveToFirst();
            int pos = cursor.getColumnIndex("Meaning");
            String val = cursor.getString(pos);
            if (val == null || val.length() < 3)
                return "null";
            else
                return val;
        }
        catch( Exception e)
        {
            e.printStackTrace();
            return "0";
        }
    }

}