package com.example.cashtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TransEditActivity extends AppCompatActivity {
    EditText etCost, etDate, etDesc;
    Button btnUpdate, btnDelete;
    Transports data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("Transport Edit");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_edit);

        etCost = findViewById(R.id.etCost);
        etDesc = findViewById(R.id.etDesc);
        etDate = findViewById(R.id.etDate);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        Intent i = getIntent();
        data = (Transports) i.getSerializableExtra("data");
        etDesc.setText(data.getDesc());
        etCost.setText(data.getCost()+"");
        etDate.setText(data.getDate());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransportDBHelper db = new TransportDBHelper(TransEditActivity.this);
                data.setDesc(etDesc.getText().toString());
                int intCost = Integer.parseInt(etCost.getText().toString());
                data.setCost(intCost);
                data.setDate(etDate.getText().toString());
                db.updateInfo(data);
                db.close();
                Intent i = new Intent();
                i.putExtra("data", data);
                setResult(RESULT_OK, i);
                finish();
                Toast.makeText(TransEditActivity.this, "Updated successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransportDBHelper db = new TransportDBHelper(TransEditActivity.this);
                db.deleteInfo(data.getId());
                db.close();
                Intent i = new Intent();
                i.putExtra("data", data);
                setResult(RESULT_OK, i);
                finish();
                Toast.makeText(TransEditActivity.this, "Deleted successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
