package com.example.cashtrack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Shopping extends AppCompatActivity {
Button btnDate;
ListView lvData;
SQLiteDatabase db;
ShoppingDBHelper shopDB;
Cursor cursor;
ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        lvData = findViewById(R.id.lvData);
        shopDB = new ShoppingDBHelper(getApplicationContext());
        db = shopDB.getReadableDatabase();
        cursor = shopDB.getAllDataForList();
        listAdapter = new ListAdapter(getApplicationContext(), R.layout.row_layout);
        lvData.setAdapter(listAdapter);
        if(cursor.moveToFirst()){
            do{
                String desc, date;
                Integer id, cost;
                id = cursor.getInt(0);
                desc = cursor.getString(1);
                cost = cursor.getInt(2);
                date = cursor.getString(3);

                ShoppingAdapter shoppingadapter = new ShoppingAdapter(id, desc, cost, date);
                listAdapter.add(shoppingadapter);


            }
            while (cursor.moveToNext());
        }
    }

    public void back(View view){
        finish();
    }

    public void addDialog(View view){
        LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewDialog = inflater.inflate(R.layout.dialog, null);

        final EditText etInputDesc = viewDialog.findViewById(R.id.shopDesc);
        final EditText etInputCost = viewDialog.findViewById(R.id.shopCost);
        final EditText etInputDate = viewDialog.findViewById(R.id.shopDate);

        final ImageView btnDatePicker = viewDialog.findViewById(R.id.btnDate);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        etInputDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                };
                // Create the DatePicker Dialog
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog myDateDialog = new DatePickerDialog(Shopping.this,
                        myDateListener, mYear, mMonth, mDay);
                myDateDialog.show();

            }
        });

        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String cDate = date.format(new Date());
        etInputDate.setText(cDate);

        AlertDialog.Builder myBuilder = new AlertDialog.Builder(Shopping.this);
        myBuilder.setView(viewDialog);
        myBuilder.setTitle("Add New");
        myBuilder.setCancelable(false);
        myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    String addDesc = etInputDesc.getText().toString();
                    String addCost = etInputCost.getText().toString();
                    String addDate = etInputDate.getText().toString();
                    int intCost = Integer.parseInt(addCost);

                    if(addDesc.isEmpty()){
                        etInputDesc.setError("Please enter a Description");
                    }
                    else if(addCost.isEmpty()){
                        etInputCost.setError("PLease enter a Cost");
                    }
                    else if(addDate.isEmpty()){
                        etInputDate.setError("Please enter a Date");
                    }
                    else{
                        ShoppingDBHelper helper = new ShoppingDBHelper(Shopping.this);
                        ArrayList<ShoppingAdapter> data = helper.getAllData();

                        long row = helper.insertData(addDesc, intCost, addDate);
                        helper.close();

                        if(row != -1){
                            Toast.makeText(Shopping.this,"Added Successfully!", Toast.LENGTH_LONG).show();
                        }
                    }
            }
        });
        myBuilder.setNegativeButton("Cancel", null);
        AlertDialog myDialog = myBuilder.create();
        myDialog.show();

    }

//    public void dateshow(View view){
//        final ImageButton btnDatePicker = findViewById(R.id.btnDate);
//        btnDatePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
////                        etInputDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                    }
//                };
//
//                // Create the DatePicker Dialog
//                Calendar c = Calendar.getInstance();
//                int mYear = c.get(Calendar.YEAR);
//                int mMonth = c.get(Calendar.MONTH);
//                int mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog myDateDialog = new DatePickerDialog(Shopping.this,
//                        myDateListener, mYear, mMonth, mDay);
//                myDateDialog.show();
//
//            }
//        });
//    }


}
