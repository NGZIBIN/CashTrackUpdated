package com.example.cashtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShopEditActivity extends AppCompatActivity {
    EditText etCost, etDate, etDesc;
    Button btnUpdate, btnDelete;
    Shoppings data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("Shopping Edit");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_edit);
        etCost = findViewById(R.id.etCost);
        etDesc = findViewById(R.id.etDesc);
        etDate = findViewById(R.id.etDate);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        Intent i = getIntent();
        data = (Shoppings) i.getSerializableExtra("data");
        etDesc.setText(data.getDesc());
        etCost.setText(data.getCost()+"");
        etDate.setText(data.getDate());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShoppingDBHelper db = new ShoppingDBHelper(ShopEditActivity.this);
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
                Toast.makeText(ShopEditActivity.this, "Updated successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingDBHelper db = new ShoppingDBHelper(ShopEditActivity.this);
                db.deleteInfo(data.getId());
                db.close();
                Intent i = new Intent();
                i.putExtra("data", data);
                setResult(RESULT_OK, i);
                finish();
                Toast.makeText(ShopEditActivity.this, "Deleted successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void back(View view){
        Intent i = new Intent(ShopEditActivity.this, Shopping.class);
        startActivity(i);
    }
}
