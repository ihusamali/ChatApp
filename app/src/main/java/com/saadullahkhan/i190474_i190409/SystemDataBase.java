package com.saadullahkhan.i190474_i190409;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class SystemDataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "system";


    private static final int DB_VERSION = 1;
    public SystemDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + "information" + " ("
                + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "userid" + " TEXT)";


        sqLiteDatabase.execSQL(query);
        query = "INSERT INTO information (userid) VALUES (0)";
        sqLiteDatabase.execSQL(query);

    }
    public void addId(String id) {



        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE information SET userid = "+id+" WHERE id = 1";
        db.execSQL(query);
        db.close();
    }
    public String getId() {

        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursor = db.rawQuery("SELECT userid FROM information WHERE id = 1", null);
        String id = "";
        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                 id = cursor.getString(0);
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursor.close();
        return id;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "information");
        onCreate(sqLiteDatabase);
    }
}
