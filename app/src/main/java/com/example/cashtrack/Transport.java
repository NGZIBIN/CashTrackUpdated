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
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Transport extends AppCompatActivity {
    TextView transCost;
    ImageButton btnDelete;
    ListView lvData;
    SQLiteDatabase db;
    TransportDBHelper transDB;
    Cursor cursor;
    ArrayList<TransportAdapter> al;
    TransportListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);

        lvData = findViewById(R.id.lvData);
//        btnDelete = findViewById(R.id.btnDelete);
        transCost = findViewById(R.id.totalCost);
        int total = 0;
        TransportDBHelper helperTrans = new TransportDBHelper(Transport.this);
        ArrayList<TransportAdapter> transData = helperTrans.getAllData();
        for(int i = 0; i < transData.size(); i ++){
            int transCost = transData.get(i).getCost();
            total = transCost + total;
        }
        String totalString = String.valueOf(total);
        transCost.setText(totalString);
        transDB = new TransportDBHelper(getApplicationContext());
        db = transDB.getReadableDatabase();
        cursor = transDB.getAllDataForList();
        listAdapter = new TransportListAdapter(getApplicationContext(), R.layout.row_layout,al);
        lvData.setAdapter(listAdapter);
        if (cursor.moveToFirst()) {
            do {
                String desc, date;
                Integer id, cost;
                id = cursor.getInt(0);
                desc = cursor.getString(1);
                cost = cursor.getInt(2);
                date = cursor.getString(3);



                TransportAdapter transportAdapter = new TransportAdapter(id, desc, cost, date);
                listAdapter.add(transportAdapter);


            }
            while (cursor.moveToNext());
        }
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

                DatePickerDialog myDateDialog = new DatePickerDialog(Transport.this,
                        myDateListener, mYear, mMonth, mDay);
                myDateDialog.show();

            }
        });

        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String cDate = date.format(new Date());
        etInputDate.setText(cDate);

        final AlertDialog myBuilder = new AlertDialog.Builder(Transport.this)
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
                    etInputDesc.requestFocus();
                    myBuilder.show();
                }
                else if(addCost.isEmpty()){
                    etInputCost.setError("PLease enter a Cost");
                    etInputCost.requestFocus();
                    myBuilder.show();

                }
                else if(addDate.isEmpty()){
                    etInputDate.setError("Please enter a Date");
                    etInputDate.requestFocus();
                    myBuilder.show();

                }
                else{
                    TransportDBHelper helper = new TransportDBHelper(Transport.this);
                    int intCost = Integer.parseInt(addCost);
                    long row = helper.insertData(addDesc, intCost, addDate);
                    helper.close();

                    if(row != -1){

                        Toast.makeText(Transport.this,"Added Successfully!", Toast.LENGTH_LONG).show();
                        transDB = new TransportDBHelper(getApplicationContext());
                        db = transDB.getReadableDatabase();
                        cursor = transDB.getAllDataForList();
                        listAdapter = new TransportListAdapter(getApplicationContext(), R.layout.row_layout,al);
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
                                transCost.setText(totalString);

                                TransportAdapter transportAdapter = new TransportAdapter(id, desc, cost, date);
                                listAdapter.add(transportAdapter);
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