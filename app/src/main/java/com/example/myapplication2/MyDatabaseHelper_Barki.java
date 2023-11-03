package com.example.myapplication2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

class MyDatabaseHelper_Barki extends SQLiteOpenHelper {
    private Context context_Barki;
    private static final String DATABASE_NAME = "Pomiary_Barki.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "moje_pomiary_Barki";
    private static final String COLUMN_ID = "_id_Barki";
    private static final String COLUMN_VALUE = "value_Barki";
    private static final String COLUMN_DATE = "date_Barki";

    MyDatabaseHelper_Barki(@Nullable Context context_Barki) {
        super(context_Barki, DATABASE_NAME, null, DATABASE_VERSION);
        this.context_Barki = context_Barki;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_VALUE + " TEXT, " + COLUMN_DATE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_DATE + " TEXT;");
        }
    }

    Cursor readAllData()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if( db != null )
        {
            cursor = db.rawQuery( query,null );
        }

        return cursor;
    }

    public long addMeasurement(String value_Barki, String date_Barki) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Sprawdź, czy wartość mieści się w zakresie od 0 do 100
            int numericValue = Integer.parseInt(value_Barki);
            if (numericValue >= 0 && numericValue <= 200) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_VALUE, value_Barki);
                values.put(COLUMN_DATE, date_Barki);

                long result = db.insert(TABLE_NAME, null, values);
                Log.d("Database", "Inserted new row with ID: " + result); // Add this line for debugging
                return result;
            } else {
                // Wartość nie mieści się w zakresie, anuluj operację
                return -1;
            }
        } catch (NumberFormatException e) {
            // Nieprawidłowy format wartości, anuluj operację
            e.printStackTrace();
            return -1;
        } finally {
            db.close();
        }
    }



    void updateData(String row_id_Barki, String value_Barki, String date_Barki) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_VALUE, value_Barki);
        cv.put(COLUMN_DATE, date_Barki);

        try {
            int result = db.update(TABLE_NAME, cv, "_id_Barki=?", new String[]{row_id_Barki});
            // Zwracaj wynik lub obsłuż ewentualny błąd
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    void deleteOneRow(String row_id_Barki) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int result = db.delete(TABLE_NAME, "_id_Barki=?", new String[]{row_id_Barki});
            // Zwracaj wynik lub obsłuż ewentualny błąd
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

}
