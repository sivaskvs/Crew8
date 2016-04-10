package com.myapplication.siva.cubbalance;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;

public class CubDb extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "cubDB.db";
    private static final String TABLE_PRODUCTS = "cubbalance";

    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_BAL = "bal";
    public static final String COLUMN_ID = "_id";
    private static String TAG="com.myapplication.siva.cubbalance";

    public CubDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        Log.i(TAG, "Super 1s");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PRODUCTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                                     + COLUMN_DATE + " TEXT,"
                                     + COLUMN_BAL  + " TEXT"+ ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }
    public boolean addDate(Date date1,String bal1) {
        ContentValues values = new ContentValues();
        Log.i(TAG, "Super 1");
        SQLiteDatabase dba = this.getWritableDatabase();
        Log.i(TAG, "Super 1aa");
        SimpleDateFormat formatter  = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE 1";
        String ret = "";
        Log.i(TAG, "Super 2");
        Log.i(TAG, "Super 2a");
        Cursor cursor = dba.rawQuery(query, null);
        Log.i(TAG, "Super 2b");
        Log.i(TAG, "Super 2c");
        cursor.moveToFirst();
        Log.i(TAG, "Super 2d");
        if (!cursor.moveToFirst()) {
            Log.i(TAG, "Super 2s");
            values.put(COLUMN_DATE, formatter.format(date1));
            values.put(COLUMN_BAL,bal1);
            dba.insert(TABLE_PRODUCTS, null, values);
            Log.i(TAG, "Product Added ");
            dba.close();
        }
        else {
            Log.i(TAG, "Super 3");
                ret += cursor.getString(cursor.getColumnIndex("date"));
                Date aDate = new Date();
                try{
                    aDate = formatter.parse(ret);
                }catch (Exception e){}
                Log.i(TAG, formatter.format(aDate));
                Log.i(TAG, formatter.format(date1));
                if (date1.after(aDate)) {
                    Log.i(TAG, "Super 4a");
                    if (cursor.moveToFirst()) {
                        Log.i(TAG, "Super 4");
                        dba.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?", new String[]{"1"});
                        String tempDate = formatter.format(date1);
                        values.put(COLUMN_DATE, tempDate);
                        values.put(COLUMN_BAL,  bal1);
                        Log.i(TAG, formatter.format(date1));
                        Log.i(TAG, tempDate);
                        dba.insert(TABLE_PRODUCTS, null, values);
                        dba.close();
                    }
                    return true;
                }
                return false;
            }
        return true;
    }

    public String giveBal()
    {
        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE 1";
        Log.i(TAG, "Inside on giveBal1");
        String ret;
        SQLiteDatabase dba = this.getWritableDatabase();
        Log.i(TAG, "Inside on giveBal 1a");
        Cursor cursor = dba.rawQuery(query, null);
        Log.i(TAG, "Inside on giveBal 1b");
        cursor.moveToFirst();
        ret = cursor.getString(cursor.getColumnIndex("bal"));
        Log.i(TAG, "Inside on giveBal 2");
        return ret;
    }

    public String giveDate()
    {
        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE 1";
        Log.i(TAG, "Inside on givedate 1");
        String ret;
        SQLiteDatabase dba = this.getWritableDatabase();
        Cursor cursor = dba.rawQuery(query, null);
        cursor.moveToFirst();
        ret =cursor.getString(cursor.getColumnIndex("date"));
        Log.i(TAG, "Inside on givedate 2");
        return ret;

    }
}

