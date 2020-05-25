package com.example.cashtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SharedPreferencesConfig sharedPreferencesConfig;
    Button btnLogin, btnRegister;
    TextView username, totalCost, borrowTotal, oweTotal;
    SQLiteDatabase dbFood, dbShop, dbTransport;
    FoodDBHelper foodDB;
    int total = 0;
    int totalFood = 0;
    int totalTrans = 0;
    int totalShop= 0;
    int borrowTot = 0;
    int oweTot = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
        username = findViewById(R.id.username);
        totalCost = findViewById(R.id.totalCost);
        borrowTotal = findViewById(R.id.borrowTotal);
        oweTotal = findViewById(R.id.oweTotal);

        Intent name = getIntent();
        String nameGet = name.getStringExtra("username");

        username.setText(nameGet);


        youOweDBHelper oweHelper = new youOweDBHelper(MainActivity.this);
        ArrayList<youOwes> oweData = oweHelper.getAllData();
        for(int i = 0; i < oweData.size(); i ++){
            int oweCost = oweData.get(i).getCost();
            oweTot = oweCost + oweTot;
        }



        youBorrowDBHelper borrowHelper = new youBorrowDBHelper(MainActivity.this);
        ArrayList<youBorrows> borrowData = borrowHelper.getAllData();
        for(int i = 0; i < borrowData.size(); i ++){
            int borrowCost = borrowData.get(i).getCost();
            borrowTot = borrowCost + borrowTot;
        }


        FoodDBHelper helper = new FoodDBHelper(MainActivity.this);
        ArrayList<Foods> data = helper.getAllData();
        for(int i = 0; i < data.size(); i ++){
            int foodCost = data.get(i).getCost();
            totalFood = foodCost + totalFood;
        }


        TransportDBHelper helperTrans = new TransportDBHelper(MainActivity.this);
        ArrayList<TransportAdapter> transData = helperTrans.getAllData();
        for(int i = 0; i < transData.size(); i ++){
            int transCost = transData.get(i).getCost();
            totalTrans = transCost + totalTrans;
        }

        ShoppingDBHelper helperShop = new ShoppingDBHelper(MainActivity.this);
        ArrayList<Shoppings> transShop = helperShop.getAllData();
        for(int i = 0; i < transShop.size(); i ++){
            int shopCost = transShop.get(i).getCost();
            totalShop = shopCost + totalShop;
        }

        total = totalFood + totalShop + totalTrans;
        String totalString = String.valueOf(total);
        totalCost.setText(totalString);


        String oweString = String.valueOf(oweTot);
        oweTotal.setText(oweString);


        String borrowString = String.valueOf(borrowTot);
        borrowTotal.setText(borrowString);

    }

//    @Override
//    public void onRestart() {
//        super.onRestart();
//        int total = 0;
//        int totalFood = 0;
//        int totalTrans = 0;
//        int totalShop= 0;
//        int borrowTot = 0;
//        int oweTot = 0;

//        youOweDBHelper oweHelper = new youOweDBHelper(MainActivity.this);
//        ArrayList<youOweAdapter> oweData = oweHelper.getAllData();
//        for(int i = 0; i < oweData.size(); i ++){
//            int oweCost = oweData.get(i).getCost();
//            oweTot = oweCost + oweTot;
//        }
//
//
//
//        youBorrowDBHelper borrowHelper = new youBorrowDBHelper(MainActivity.this);
//        ArrayList<youBorrowAdapter> borrowData = borrowHelper.getAllData();
//        for(int i = 0; i < borrowData.size(); i ++){
//            int borrowCost = borrowData.get(i).getCost();
//            borrowTot = borrowCost + borrowTot;
//        }
//
//
//        FoodDBHelper helper = new FoodDBHelper(MainActivity.this);
//        ArrayList<FoodAdapter> data = helper.getAllData();
//        for(int i = 0; i < data.size(); i ++){
//            int foodCost = data.get(i).getCost();
//            totalFood = foodCost + totalFood;
//        }
//
//
//        TransportDBHelper helperTrans = new TransportDBHelper(MainActivity.this);
//        ArrayList<TransportAdapter> transData = helperTrans.getAllData();
//        for(int i = 0; i < transData.size(); i ++){
//            int transCost = transData.get(i).getCost();
//            totalTrans = transCost + totalTrans;
//        }
//
//        ShoppingDBHelper helperShop = new ShoppingDBHelper(MainActivity.this);
//        ArrayList<ShoppingAdapter> transShop = helperShop.getAllData();
//        for(int i = 0; i < transShop.size(); i ++){
//            int shopCost = transShop.get(i).getCost();
//            totalShop = shopCost + totalShop;
//        }
//
//        total = totalFood + totalShop + totalTrans;
//        String totalString = String.valueOf(total);
//        totalCost.setText(totalString);
//
//
//        String oweString = String.valueOf(oweTot);
//        oweTotal.setText(oweString);
//
//
//        String borrowString = String.valueOf(borrowTot);
//        borrowTotal.setText(borrowString);
//    }


    public void back(View view){
        finish();
    }

    public void goShopping(View view){
        Intent intent = new Intent(MainActivity.this, Shopping.class);
        startActivity(intent);
    }

    public void goProfile(View view){
        Intent ans = getIntent();
        String name = ans.getStringExtra("username");
        Intent intent = new Intent(MainActivity.this, Profile.class);
        intent.putExtra("username", name);
        startActivity(intent);
    }

    public void goFood(View view){
        Intent intent = new Intent(MainActivity.this, Food.class);
        startActivity(intent);
    }

    public void goTransport(View view){
        Intent intent = new Intent(MainActivity.this, Transport.class);
        startActivity(intent);
    }

    public void goYouOwe(View view){
        Intent intent = new Intent(MainActivity.this, youOwe.class);
        startActivity(intent);
    }

    public void goBorrow(View view){
        Intent intent = new Intent(MainActivity.this, youBorrow.class);
        startActivity(intent);
    }

    public void goGraph(View view){
        Intent intent = new Intent(MainActivity.this, totalSpendStats.class);
        intent.putExtra("foodCost", totalFood);
        intent.putExtra("transCost", totalTrans);
        intent.putExtra("shopCost", totalShop);

        startActivity(intent);
    }



}
