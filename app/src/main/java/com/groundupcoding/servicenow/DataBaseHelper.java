package com.groundupcoding.servicenow;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.groundupcoding.servicenow.models.Instance;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;


@ReportsCrashes(
        formKey="dGVacG0ydVHnaNHjRjVTUTEtb3FPWGc6MQ",
        formUri = "http://atxcoder.ddns.net:3700/acra-bugtest/_design/acra-storage/_update/report",
        reportType = org.acra.sender.HttpSender.Type.JSON,
        httpMethod = org.acra.sender.HttpSender.Method.PUT,
        formUriBasicAuthLogin="acra_report",
        formUriBasicAuthPassword="password",
        mode = ReportingInteractionMode.DIALOG,
        resToastText = R.string.crash_toast_text, // optional, displayed as soon as the crash occurs, before collecting data which can take a few seconds
        resDialogText = R.string.crash_dialog_text,
        resDialogIcon = android.R.drawable.ic_dialog_info, //optional. default is a warning sign
        resDialogTitle = R.string.crash_dialog_title, // optional. default is your application name
        resDialogCommentPrompt = R.string.crash_dialog_comment_prompt, // optional. when defined, adds a user text field input with this text resource as a label
        resDialogOkToast = R.string.crash_dialog_ok_toast // optional. displays a Toast message when the user accepts to send a report.
)

/**
 * Created by Thomas on 1/23/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "ServiceNowDB.db";
    public static final String TABLE_INSTANCE = "instance";
    public static final String TABLE_SETTINGS = "settings";


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";

    public static final String COLUMN_USERNAME = "userName";
    public static final String COLUMN_LOGIN = "loginTime";
    public static final String COLUMN_INSTANCE = "currentInstance";
    public static final String COLUMN_PASSWORD = "password";


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

        String DROP_SETTINGS = "DROP TABLE IF EXISTS " + TABLE_SETTINGS;

        String CREATE_SETTINGS_TABLE = "CREATE TABLE " +
                TABLE_SETTINGS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_LOGIN + " TEXT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_INSTANCE + " TEXT)";

        db.execSQL(CREATE_INSTANCE_TABLE);
        db.execSQL(DROP_SETTINGS);
        db.execSQL(CREATE_SETTINGS_TABLE);

    }

    public void resetCredentials()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String DROP_SETTINGS = "DROP TABLE IF EXISTS " + TABLE_SETTINGS;
        String CREATE_SETTINGS_TABLE = "CREATE TABLE " +
                TABLE_SETTINGS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_LOGIN + " TEXT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_INSTANCE + " TEXT)";

        db.execSQL(DROP_SETTINGS);
        db.execSQL(CREATE_SETTINGS_TABLE);
        Log.i("ServiceNow","Credentials table dropped and re-created");

    }

    public void setCredentials(String username, String password, String instance)
    {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        dateFormatter.setLenient(false);
        Date today = new Date();
        String s = dateFormatter.format(today);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_INSTANCE, instance);
        values.put(COLUMN_LOGIN, s);
        db.insert(TABLE_SETTINGS, null, values);
        db.close();
        Log.i("ServiceNow","Credentials and settings table populated");
    }

    public Cursor getCredentials()
    {
        String query = "SELECT username, password FROM " + TABLE_SETTINGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Closing connection



        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
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

        return cursor;
    }

    public String getInstanceURL(long id)
    {
        String _name = null;

        // Select All Query
        String selectQuery = "SELECT address FROM " + TABLE_INSTANCE + " where _id = '" + id + "'";

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

    public String getCurrentInstance()
    {
        String currentInstance = null;
        String query = "SELECT " + COLUMN_INSTANCE + " FROM " + TABLE_SETTINGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();
        currentInstance = cursor.getString(0);

        cursor.close();
        db.close();

        return currentInstance;
    }
}
