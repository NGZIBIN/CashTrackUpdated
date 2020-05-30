package com.example.cashtrack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class youOwe extends AppCompatActivity {

    ListView lvData;
    SQLiteDatabase db;
    youOweDBHelper oweDB;
    Cursor cursor;
    youOweListAdapter listAdapter;
    ArrayList<youOwes> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("Owe");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_owe);
        lvData = findViewById(R.id.lvData);


        oweDB = new youOweDBHelper(getApplicationContext());
        db = oweDB.getReadableDatabase();
        cursor = oweDB.getAllDataForList();
        listAdapter = new youOweListAdapter(getApplicationContext(), R.layout.row_layout,al);
        lvData.setAdapter(listAdapter);
        if(cursor.moveToFirst()){
            do{
                String desc, date, name;
                Integer id, cost;
                id = cursor.getInt(0);
                desc = cursor.getString(1);
                name = cursor.getString(2);
                cost = cursor.getInt(3);
                date = cursor.getString(4);



                youOwes youOweAdapter = new youOwes(id, name, desc, cost, date);
                listAdapter.add(youOweAdapter);
                listAdapter.notifyDataSetChanged();

            }
            while (cursor.moveToNext());
        }
        al = new ArrayList<youOwes>();
        final ArrayList<youOwes> youOwes = oweDB.getAllData();
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                youOwes data = youOwes.get(i);
                Intent intent = new Intent(youOwe.this, youOweEdit.class);
                intent.putExtra("data", data);
                startActivityForResult(intent, 7);
            }
        });


    }
    public void addDialog(View view){
        LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewDialog = inflater.inflate(R.layout.owe_dialog, null);

        final EditText etInputDesc = viewDialog.findViewById(R.id.shopDesc);
        final EditText etInputCost = viewDialog.findViewById(R.id.shopCost);
        final EditText etInputDate = viewDialog.findViewById(R.id.shopDate);
        final EditText etInputName = viewDialog.findViewById(R.id.oweName);

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

                DatePickerDialog myDateDialog = new DatePickerDialog(youOwe.this,
                        myDateListener, mYear, mMonth, mDay);
                myDateDialog.show();

            }
        });

        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String cDate = date.format(new Date());
        etInputDate.setText(cDate);

        final AlertDialog myBuilder = new AlertDialog.Builder(youOwe.this).setView(viewDialog)
                .setTitle("Add New")
                .setCancelable(false)
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", null).show();

        Button positiveButton = myBuilder.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String addDesc = etInputDesc.getText().toString();
                String addCost = etInputCost.getText().toString();
                String addDate = etInputDate.getText().toString();
                String addName = etInputName.getText().toString();
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
                else if(addName.isEmpty()){
                    etInputName.setError("Please enter a Name");
                }
                else{
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.SECOND, 5);

                    Intent i = new Intent(youOwe.this, ScheduledNotiOwe.class);
                    int reqCode = 123;

                    i.putExtra("name", etInputName.getText().toString());
                    i.putExtra("cost", etInputCost.getText().toString());
                    i.putExtra("desc", etInputDesc.getText().toString());
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(youOwe.this,reqCode,
                            i, PendingIntent.FLAG_CANCEL_CURRENT);

                    AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                    youOweDBHelper helper = new youOweDBHelper(youOwe.this);

                    long row = helper.insertData1(addDesc, addName, intCost, addDate);
                    helper.close();

                    if(row != -1){
                        Toast.makeText(youOwe.this,"Added Successfully!", Toast.LENGTH_LONG).show();
                        oweDB = new youOweDBHelper(getApplicationContext());
                        db = oweDB.getReadableDatabase();
                        cursor = oweDB.getAllDataForList();
                        listAdapter = new youOweListAdapter(getApplicationContext(), R.layout.row_layout,al);
                        lvData.setAdapter(listAdapter);
                        if(cursor.moveToFirst()){
                            do{
                                String desc, date, name;
                                Integer id, cost;
                                id = cursor.getInt(0);
                                desc = cursor.getString(1);
                                name = cursor.getString(2);
                                cost = cursor.getInt(3);
                                date = cursor.getString(4);


                                youOwes youOweAdapter = new youOwes(id, name, desc, cost, date);
                                listAdapter.add(youOweAdapter);
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
        if (resultCode == RESULT_OK && requestCode == 7) {
            oweDB = new youOweDBHelper(getApplicationContext());
            db = oweDB.getReadableDatabase();
            cursor = oweDB.getAllDataForList();
            listAdapter = new youOweListAdapter(getApplicationContext(), R.layout.row_layout, al);
            lvData.setAdapter(listAdapter);
            if(cursor.moveToFirst()){
                do{
                    String desc, date, name;
                    Integer id, cost;
                    id = cursor.getInt(0);
                    desc = cursor.getString(1);
                    name = cursor.getString(2);
                    cost = cursor.getInt(3);
                    date = cursor.getString(4);



                    youOwes youowe = new youOwes(id, desc,name, cost, date);
                    listAdapter.add(youowe);
                    listAdapter.notifyDataSetChanged();

                }
                while (cursor.moveToNext());
            }
            listAdapter.notifyDataSetChanged();
        }

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

