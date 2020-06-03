package com.example.cashtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class youOweEdit extends AppCompatActivity {
    EditText etCost, etDate, etDesc, etName;
    Button btnUpdate, btnDelete;
    youOwes data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("Owe Edit");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_owe_edit);
        etCost = findViewById(R.id.etCost);
        etDesc = findViewById(R.id.etDesc);
        etDate = findViewById(R.id.etDate);
        etName = findViewById(R.id.etName);

        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        Intent i = getIntent();
        data = (youOwes) i.getSerializableExtra("data");
        etDesc.setText(data.getDesc());
        etCost.setText(data.getCost()+"");
        etDate.setText(data.getDate());
        etName.setText(data.getName());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youOweDBHelper db = new youOweDBHelper(youOweEdit.this);
                data.setDesc(etDesc.getText().toString());
                int intCost = Integer.parseInt(etCost.getText().toString());
                data.setCost(intCost);
                data.setName(etName.getText().toString());
                data.setDate(etDate.getText().toString());
                db.updateInfo(data);
                db.close();
                Intent i = new Intent();
                i.putExtra("data", data);
                setResult(RESULT_OK, i);
                finish();
                Toast.makeText(youOweEdit.this, "Updated successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youOweDBHelper db = new youOweDBHelper(youOweEdit.this);
                db.deleteInfo(data.getId());
                db.close();
                Intent i = new Intent();
                i.putExtra("data", data);
                setResult(RESULT_OK, i);
                finish();
                Toast.makeText(youOweEdit.this, "Deleted successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goMessage(View view){
        String msgCost = etCost.getText().toString().trim();
        String msgDesc = etDesc.getText().toString();
        String msgDate = etDate.getText().toString();
        String msgName = etName.getText().toString();

        Intent i = new Intent(youOweEdit.this, oweMessageNoti.class);
        i.putExtra("name", msgName);
        i.putExtra("date", msgDate);
        i.putExtra("desc", msgDesc);
        i.putExtra("cost", msgCost);
        startActivity(i);
    }

    public void back(View view){
        Intent i = new Intent(youOweEdit.this, youOwe.class);
        startActivity(i);
    }

}
