package com.example.courework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.courework.models.Hike;
import com.example.courework.models.Observation;

import java.io.ByteArrayOutputStream;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imgByte;
    private static final String DATABASE_NAME = "Hiker.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME_HIKER = "hiker_db";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_DOH = "date_of_the_hike";
    private static final String COLUMN_PARKING = "parking";
    private static final String COLUMN_LENGTH = "length";
    private static final String COLUMN_LEVEL = "difficulty_level";
    private static final String COLUMN_DESCRIPTION = "description";


    private static final String TABLE_NAME_OB = "observation_db";
    private static final String COLUMN_OID = "o_id";
    private static final String COLUMN_ONAME = "o_name";
    private static final String COLUMN_TOO = "time_of_ob";
    private static final String COLUMN_OCOMMENT = "o_comment";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_HIKER_ID = "hiker_id";



    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_hiker =
                "CREATE TABLE " + TABLE_NAME_HIKER +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_LOCATION + " TEXT, " +
                        COLUMN_DOH + " TEXT, " +
                        COLUMN_PARKING + " TEXT, " +
                        COLUMN_LENGTH + " INTEGER, " +
                        COLUMN_LEVEL + " TEXT, " +
                        COLUMN_DESCRIPTION + " TEXT);";


        String query_ob =
                "CREATE TABLE " + TABLE_NAME_OB +
                        " (" + COLUMN_OID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ONAME + " TEXT, " +
                        COLUMN_TOO + " TEXT, " +
                        COLUMN_OCOMMENT + " TEXT, " +
                        COLUMN_IMAGE + " BLOB, " +
                        COLUMN_HIKER_ID + " TEXT, " +
                        " FOREIGN KEY ("+COLUMN_HIKER_ID+") REFERENCES "+TABLE_NAME_HIKER+"("+COLUMN_ID+"));";

        try {
            db.execSQL(query_hiker);
            db.execSQL(query_ob);
            Toast.makeText(context, "Table created successfully!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context, "Table created failed!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_HIKER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_OB);
        onCreate(db);
    }


    //===============================================HIKER======================================================
    public void addHike(Hike hike){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, hike.getId());
        cv.put(COLUMN_NAME, hike.getName());
        cv.put(COLUMN_LOCATION, hike.getLocation());
        cv.put(COLUMN_DOH, hike.getDoh());
        cv.put(COLUMN_PARKING, hike.getParking());
        cv.put(COLUMN_LENGTH, hike.getLength());
        cv.put(COLUMN_LEVEL, hike.getLevel());
        cv.put(COLUMN_DESCRIPTION, hike.getDescription());
        long result = db.insert(TABLE_NAME_HIKER,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllDataHike(){
        String query = "SELECT * FROM " + TABLE_NAME_HIKER;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateDataHike(Hike hike){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, hike.getName());
        cv.put(COLUMN_LOCATION, hike.getLocation());
        cv.put(COLUMN_DOH, hike.getDoh());
        cv.put(COLUMN_PARKING, hike.getParking());
        cv.put(COLUMN_LENGTH, hike.getLength());
        cv.put(COLUMN_LEVEL, hike.getLevel());
        cv.put(COLUMN_DESCRIPTION, hike.getDescription());
        long result = db.update(TABLE_NAME_HIKER, cv, "_id=?", new String[]{hike.getId()});
        if (result == -1){
            Toast.makeText(context, "Failed to update.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully Updated.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRowHike(String row_id){
        SQLiteDatabase db = getWritableDatabase();
        long result = db.delete(TABLE_NAME_HIKER, "_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Failed to delete.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllDataHike(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_HIKER);
    }


    //============================================OBSERVATION==========================================================

    public void addObservation(Observation observation){


        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap imgToStoreBitmap = observation.getImage();
            byteArrayOutputStream = new ByteArrayOutputStream();
            imgToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            imgByte = byteArrayOutputStream.toByteArray();

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_OID, observation.getId());
            cv.put(COLUMN_ONAME, observation.getName());
            cv.put(COLUMN_TOO, observation.getTimeOfOb());
            cv.put(COLUMN_OCOMMENT, observation.getComment());
            cv.put(COLUMN_IMAGE, imgByte);
            cv.put(COLUMN_HIKER_ID, observation.getHiker_id());
            long result = db.insert(TABLE_NAME_OB,null, cv);
            if(result == -1){
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public Cursor readAllDataOb(){
        String query = "SELECT * FROM " + TABLE_NAME_OB;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateDataOb(Observation observation){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap imgToStoreBitmap = observation.getImage();
            byteArrayOutputStream = new ByteArrayOutputStream();
            imgToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            imgByte = byteArrayOutputStream.toByteArray();

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_OID, observation.getId());
            cv.put(COLUMN_ONAME, observation.getName());
            cv.put(COLUMN_TOO, observation.getTimeOfOb());
            cv.put(COLUMN_OCOMMENT, observation.getComment());
            cv.put(COLUMN_IMAGE, imgByte);
            cv.put(COLUMN_HIKER_ID, observation.getHiker_id());

            long result = db.update(TABLE_NAME_OB, cv, "o_id=?", new String[]{observation.getId()});
            if (result == -1){
                Toast.makeText(context, "Failed to update.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Successfully Updated.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRowOb(String row_id){
        SQLiteDatabase db = getWritableDatabase();
        long result = db.delete(TABLE_NAME_OB, "o_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Failed to delete.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllDataOb(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_OB);
    }



}
