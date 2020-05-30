package com.example.cashtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class login extends AppCompatActivity {
    private SharedPreferencesConfig sharedPreferencesConfig;
    EditText etPass, etName;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("CashTrack Login");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etName = findViewById(R.id.etName);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String pass = etPass.getText().toString();
                sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());

//                if(sharedPreferencesConfig.read_login_status()){
//                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }

                LoginDBHelper helper = new LoginDBHelper(login.this);
                ArrayList<Accounts> data = helper.getAllAccount();

                for (int i = 0; i < data.size(); i++) {
                    String dbName = data.get(i).getName();
                    String dbPass = data.get(i).getPass();

                    if(name.isEmpty()){
                        etName.setError("Please enter a username");
                    }
                    else if(pass.isEmpty()){
                        etPass.setError("Please enter a password");
                    }
                    else{
                        if(name.equals(dbName) && (pass.equals(dbPass))){
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            intent.putExtra("username", name);
                            intent.putExtra("pass", pass);
                            startActivity(intent);
                            Toast.makeText(login.this, "Welcome back " + name, Toast.LENGTH_LONG).show();
                        }
                        else if(name.equals(dbName) && (pass != dbPass)){
                            Toast.makeText(login.this, "Wrong Password Entered", Toast.LENGTH_LONG).show();
                        }
                        else if(name != (dbName) && (pass.equals(dbPass))){
                            Toast.makeText(login.this, "Wrong Username Entered", Toast.LENGTH_LONG).show();
                        }
//                        else{
//                            Toast.makeText(login.this, "Account does not exist. Please register a account.", Toast.LENGTH_LONG).show();
//
//                        }
                    }
            }
        }
    });
    }

    public void goRegister(View view){
        Intent intent = new Intent(login.this,register.class);
        startActivity(intent);
    }
    public void back(View view){
        finish();
    }
}

