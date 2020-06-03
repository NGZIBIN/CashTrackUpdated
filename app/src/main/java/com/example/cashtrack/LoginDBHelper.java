package com.example.cashtrack;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LoginDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "login";
    private static final String TABLE_ACC = "account";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUM = "number";
    private static final String COLUMN_PASS = "pass";

    public LoginDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_ACC + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_NUM + " INTEGER,"
                + COLUMN_PASS + " TEXT )";
        db.execSQL(createTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACC);
        onCreate(db);
    }



    public long insertAccount(String name, int number, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);

        values.put(COLUMN_NUM, number);

        values.put(COLUMN_PASS, pass);

        db.insert(TABLE_ACC, null, values);

        long result = db.insert(TABLE_ACC, null, values);

        db.close();
        if (result == -1){
            Log.d("DBHelper", "Insert fail");
        } else {
            Log.d("SQL  Insert ",""+ result);
        }

        return result;
    }

    public ArrayList<Accounts> getAllAccount() {
        ArrayList<Accounts> accounts = new ArrayList<Accounts>();

        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_NAME + ", "
                + COLUMN_NUM + ", "
                + COLUMN_PASS
                + " FROM " + TABLE_ACC;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int number = cursor.getInt(2);
                String pass = cursor.getString(3);
                Accounts obj = new Accounts(id, name, number, pass);
                accounts.add(obj);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();

        return accounts;
    }
}
