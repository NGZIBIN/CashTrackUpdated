package com.example.cashtrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class oweMessageNoti extends AppCompatActivity {

    Button btnSend;
    EditText etNum, etMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("Message");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_message_noti);

        ActivityCompat.requestPermissions(oweMessageNoti.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS},
        PackageManager.PERMISSION_GRANTED);

        btnSend = findViewById(R.id.btnSend);
        etNum = findViewById(R.id.etNum);
        etMsg = findViewById(R.id.etMsg);

        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String date = i.getStringExtra("date");
        String cost = i.getStringExtra("cost");
        String desc = i.getStringExtra("desc");

        String message = "Hello, " + name + " you borrowed $" + cost + " on " + date + " from me for " + desc + ". Please return back to me as soon as you can. Thank you!";

        etMsg.setText(message);
    }

    public void sendSMS(View view){

        String message = etMsg.getText().toString();
        String number = etNum.getText().toString();

        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(number, null, message, null, null);
        Toast.makeText(oweMessageNoti.this, "Message send successfully",
                Toast.LENGTH_SHORT).show();

        finish();
    }
    public void back(View view){
        Intent i = new Intent(oweMessageNoti.this, youBorrow.class);
        startActivity(i);
    }
}
