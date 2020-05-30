package com.example.cashtrack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    TextView profileName, tvUsername, tvPass;
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

        Intent ans = getIntent();
        String name = ans.getStringExtra("username");
        String pass = ans.getStringExtra("pass");

        tvUsername.setText(name);
        tvPass.setText(pass);

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
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(Profile.this);
        myBuilder.setTitle("Alert!");
        myBuilder.setMessage("Are you sure you want to logout?");
        myBuilder.setCancelable(false);
        myBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Profile.this, login.class);
                Toast.makeText(Profile.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
        myBuilder.setNegativeButton("No", null);
        AlertDialog myDialog = myBuilder.create();
        myDialog.show();



    }
}
