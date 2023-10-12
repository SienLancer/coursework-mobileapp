package com.example.courework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Hiker.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "hiker_db";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_DOH = "date_of_the_hike";
    private static final String COLUMN_PARKING = "parking";
    private static final String COLUMN_LENGTH = "length";
    private static final String COLUMN_LEVEL = "difficulty_level";
    private static final String COLUMN_DESCRIPTION = "description";
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_LOCATION + " TEXT, " +
                        COLUMN_DOH + " TEXT, " +
                        COLUMN_PARKING + " TEXT, " +
                        COLUMN_LENGTH + " INTEGER, " +
                        COLUMN_LEVEL + " TEXT, " +
                        COLUMN_DESCRIPTION + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addHiker(String name, String location, String doh, String parking, int length, String level, String des){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_LOCATION, location);
        cv.put(COLUMN_DOH, doh);
        cv.put(COLUMN_PARKING, parking);
        cv.put(COLUMN_LENGTH, length);
        cv.put(COLUMN_LEVEL, level);
        cv.put(COLUMN_DESCRIPTION, des);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateData(String row_id, String name, String location, String doh, String parking,
                           int length, String level, String des){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_LOCATION, location);
        cv.put(COLUMN_DOH, doh);
        cv.put(COLUMN_PARKING, parking);
        cv.put(COLUMN_LENGTH, length);
        cv.put(COLUMN_LEVEL, level);
        cv.put(COLUMN_DESCRIPTION, des);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Failed to update.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully Updated.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Failed to delete.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
