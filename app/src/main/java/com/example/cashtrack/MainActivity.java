package com.example.cashtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void back(View view){
        finish();
    }

    public void goShopping(View view){
        Intent intent = new Intent(MainActivity.this, Shopping.class);
        startActivity(intent);
    }
}
