package com.example.cashtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FoodEditActivity extends AppCompatActivity {
EditText etCost, etDate, etDesc;
Button btnUpdate, btnDelete;
Foods data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);
        etCost = findViewById(R.id.etCost);
        etDesc = findViewById(R.id.etDesc);
        etDate = findViewById(R.id.etDate);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        Intent i = getIntent();
        data = (Foods) i.getSerializableExtra("data");
        etDesc.setText(data.getDesc());
        etCost.setText(data.getCost()+"");
        etDate.setText(data.getDate());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FoodDBHelper db = new FoodDBHelper(FoodEditActivity.this);
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
                Toast.makeText(FoodEditActivity.this, "Updated successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodDBHelper db = new FoodDBHelper(FoodEditActivity.this);
                db.deleteInfo(data.getId());
                db.close();
                Intent i = new Intent();
                i.putExtra("data", data);
                setResult(RESULT_OK, i);
                finish();
                Toast.makeText(FoodEditActivity.this, "Deleted successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
