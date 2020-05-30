package com.example.cashtrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TransportDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "transCost";
    private static final String TABLE_TRANSPORT = "transport";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESC = "description";
    private static final String COLUMN_COST = "cost";
    private static final String COLUMN_DATE = "date";
    public TransportDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_TRANSPORT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DESC + " TEXT,"
                + COLUMN_COST + " INTEGER,"
                + COLUMN_DATE + " TEXT )";
        db.execSQL(createTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSPORT);
        onCreate(db);
    }



    public long insertData(String description, int cost, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_DESC, description);

        values.put(COLUMN_COST, cost);

        values.put(COLUMN_DATE, date);


        long result = db.insert(TABLE_TRANSPORT, null, values);

        db.close();
        if (result == -1){
            Log.d("DBHelper", "Insert fail");
        } else {
            Log.d("SQL  Insert ",""+ result);
        }

        return result;
    }

    public ArrayList<Transports> getAllData() {
        ArrayList<Transports> transData = new ArrayList<Transports>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_DESC + " , "
                + COLUMN_COST + " , "
                + COLUMN_DATE
                + " FROM " + TABLE_TRANSPORT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String desc = cursor.getString(1);
                int cost = cursor.getInt(2);
                String date = cursor.getString(3);
                Transports obj = new Transports(id,desc,cost,date);
                transData.add(obj);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();

        return transData;
    }

    public Cursor getAllDataForList(){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_DESC + " , "
                + COLUMN_COST + " , "
                + COLUMN_DATE
                + " FROM " + TABLE_TRANSPORT;
        Cursor result = db.rawQuery(selectQuery, null);
        return result;
    }

    public int updateInfo(Transports data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESC, data.getDesc());
        values.put(COLUMN_DATE, data.getDate());
        values.put(COLUMN_COST, data.getCost());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_TRANSPORT, values, condition, args);
        db.close();
        return result;
    }

    public int deleteInfo(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_TRANSPORT, condition, args);
        db.close();
        return result;
    }
}
