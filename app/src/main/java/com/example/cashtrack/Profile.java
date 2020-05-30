package com.example.cashtrack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    TextView profileName, tvUsername, tvPass, tvTotal;
    private SharedPreferencesConfig sharedPreferencesConfig;
    private CircleImageView profileImage;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("Profile");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileName = findViewById(R.id.profileName);
        tvUsername = findViewById(R.id.tvUsername);
        tvPass = findViewById(R.id.tvNumber);
        tvTotal = findViewById(R.id.tvTotalSpend);
        Intent ans = getIntent();
        String name = ans.getStringExtra("username");
        String pass = ans.getStringExtra("pass");
        String total = ans.getStringExtra("total");
        tvUsername.setText(name);
        tvPass.setText(pass);
        tvTotal.setText(total);
        profileName.setText(name);
        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
        profileImage = findViewById(R.id.profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImage.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    public void goLogout(View view){
//        sharedPreferencesConfig.login_status(false);

        startActivity(new Intent(this, login.class));
        finish();
    }
}
