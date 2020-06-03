package com.example.cashtrack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Shopping extends AppCompatActivity {
TextView totalCost;
ListView lvData;
SQLiteDatabase db;
ShoppingDBHelper shopDB;
Cursor cursor;
ArrayList<Shoppings> al;
ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("Shopping");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        lvData = findViewById(R.id.lvData);
        totalCost = findViewById(R.id.totalCost);

        int total = 0;

        ShoppingDBHelper helperShop = new ShoppingDBHelper(Shopping.this);
        ArrayList<Shoppings> transShop = helperShop.getAllData();
        for(int i = 0; i < transShop.size(); i ++){
            int shopCost = transShop.get(i).getCost();
            total = shopCost + total;
        }
        String totalString = String.valueOf(total);
        totalCost.setText(totalString);

        shopDB = new ShoppingDBHelper(getApplicationContext());
        db = shopDB.getReadableDatabase();
        cursor = shopDB.getAllDataForList();
        listAdapter = new ListAdapter(getApplicationContext(), R.layout.row_layout, al);
        lvData.setAdapter(listAdapter);
        if(cursor.moveToFirst()){
            do{
                String desc, date;
                Integer id, cost;
                id = cursor.getInt(0);
                desc = cursor.getString(1);
                cost = cursor.getInt(2);
                date = cursor.getString(3);



                Shoppings shoppingadapter = new Shoppings(id, desc, cost, date);
                listAdapter.add(shoppingadapter);


            }
            while (cursor.moveToNext());
        }

        al = new ArrayList<Shoppings>();
        final ArrayList<Shoppings> shop = shopDB.getAllData();
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shoppings data = shop.get(i);
                Intent intent = new Intent(Shopping.this, ShopEditActivity.class);
                intent.putExtra("data", data);
                startActivityForResult(intent, 8);
            }
        });


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

        final AlertDialog myBuilder = new AlertDialog.Builder(Shopping.this)
     .setView(viewDialog)
        .setTitle("Add New")
        .setCancelable(false)
        .setPositiveButton("Add", null)
        .setNegativeButton("Cancel", null).show();
        Button positiveButton = myBuilder.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int total = 0;
                String addDesc = etInputDesc.getText().toString();
                String addCost = etInputCost.getText().toString();
                String addDate = etInputDate.getText().toString();


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
                    int intCost = Integer.parseInt(addCost);
                    long row = helper.insertData(addDesc, intCost, addDate);
                    helper.close();

                    if(row != -1){

                        Toast.makeText(Shopping.this,"Added Successfully!", Toast.LENGTH_LONG).show();
                        shopDB = new ShoppingDBHelper(getApplicationContext());
                        db = shopDB.getReadableDatabase();
                        cursor = shopDB.getAllDataForList();
                        listAdapter = new ListAdapter(getApplicationContext(), R.layout.row_layout,al);
                        lvData.setAdapter(listAdapter);
                        if(cursor.moveToFirst()){
                            do{
                                String desc, date;
                                Integer id, cost;
                                id = cursor.getInt(0);
                                desc = cursor.getString(1);
                                cost = cursor.getInt(2);
                                date = cursor.getString(3);


                                total += intCost;
                                String totalString = String.valueOf(total);
                                totalCost.setText(totalString);

                                Shoppings shoppingadapter = new Shoppings(id, desc, cost, date);
                                listAdapter.add(shoppingadapter);
                                listAdapter.notifyDataSetChanged();

                            }
                            while (cursor.moveToNext());
                        }
                        listAdapter.notifyDataSetChanged();
                    }
                    myBuilder.dismiss();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int total = 0;

        ShoppingDBHelper helperShop = new ShoppingDBHelper(Shopping.this);
        ArrayList<Shoppings> transShop = helperShop.getAllData();
        for(int i = 0; i < transShop.size(); i ++){
            int shopCost = transShop.get(i).getCost();
            total = shopCost + total;
        }
        String totalString = String.valueOf(total);
        totalCost.setText(totalString);
        if (resultCode == RESULT_OK && requestCode == 8) {
            shopDB = new ShoppingDBHelper(getApplicationContext());
            db = shopDB.getReadableDatabase();
            cursor = shopDB.getAllDataForList();
            listAdapter = new ListAdapter(getApplicationContext(), R.layout.row_layout, al);
            lvData.setAdapter(listAdapter);
            if(cursor.moveToFirst()){
                do{
                    String desc, date;
                    Integer id, cost;
                    id = cursor.getInt(0);
                    desc = cursor.getString(1);
                    cost = cursor.getInt(2);
                    date = cursor.getString(3);



                    Shoppings shoppings = new Shoppings(id, desc, cost, date);
                    listAdapter.add(shoppings);
                    listAdapter.notifyDataSetChanged();

                }
                while (cursor.moveToNext());
            }
            listAdapter.notifyDataSetChanged();
        }

    }

    public void back(View view){
        refreshActivity();
    }

    public void refreshActivity() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

    }
    @Override
    public void onBackPressed() {
        refreshActivity();
        super.onBackPressed();
    }



}
