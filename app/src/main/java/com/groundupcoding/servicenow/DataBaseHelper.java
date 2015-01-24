package com.groundupcoding.servicenow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.groundupcoding.servicenow.models.Instance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 1/23/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ServiceNowDB.db";
    public static final String TABLE_INSTANCE = "instance";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";


    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INSTANCE_TABLE = "CREATE TABLE " +
                TABLE_INSTANCE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME
                + " TEXT," + COLUMN_ADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_INSTANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTANCE);
        onCreate(db);
    }

    public void addInstance(Instance instance)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, instance.getName());
        values.put(COLUMN_ADDRESS, instance.getAddress());
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_INSTANCE, null, values);
        db.close();
    }

    public Cursor getInstances()
    {
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_INSTANCE;

        // Query the database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Closing connection
        cursor.close();
        db.close();

        return cursor;
    }

    public String getInstanceURL(String name)
    {
        String _name = null;

        // Select All Query
        String selectQuery = "SELECT address FROM " + TABLE_INSTANCE + " where name = '" + name + "'";

        // Query the database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        _name = cursor.getString(0);

        // Closing connection
        cursor.close();
        db.close();

        return _name;
    }
}
