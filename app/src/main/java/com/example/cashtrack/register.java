package com.example.cashtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class register extends AppCompatActivity {

    EditText etNum, etPass, etName;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etNum = findViewById(R.id.etNum);
        etPass = findViewById(R.id.etPass);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = etName.getText().toString();
                String newPass = etPass.getText().toString();
                String newNum = etNum.getText().toString();
                int intNum = Integer.parseInt(newNum);

                int numCount = 0;
                for (int i = 0; i < newNum.length(); i++) {
                    if (Character.isDigit(newNum.charAt(i))) {
                        numCount++;
                    }
                }

                if(newName.isEmpty()){
                    etName.setError("Please enter a name!");
                }
                else if(newNum.isEmpty()){
                    etNum.setError("Please enter a contact number");
                }
                else if(newPass.isEmpty()){
                    etPass.setError("Please enter a password!");
                }
                else if(numCount > 8){
                    etPass.setError("Please enter a valid contact number");
                }
                else{
                    LoginDBHelper helper = new LoginDBHelper(register.this);
                    ArrayList<Accounts> data = helper.getAllAccount();

                    long row = helper.insertAccount(newName,intNum,newPass);
                    helper.close();

                    if(row != -1){
                        Toast.makeText(register.this,"Account Successfully Created!", Toast.LENGTH_LONG).show();
                    }

                    for(int i = 0; i < data.size(); i++) {
                        String name = data.get(i).getName();
                        int num = data.get(i).getNum();
                        String sNum = String.valueOf(num);

                        if (newName.equals(name) && newNum.equals(sNum)) {
                            Toast.makeText(register.this, "Existing Account", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }
    public void back(View view){
        finish();
    }
}
