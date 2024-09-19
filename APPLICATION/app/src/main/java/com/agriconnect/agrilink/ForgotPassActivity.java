package com.agriconnect.agrilink;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ForgotPassActivity extends AppCompatActivity {
    private ImageView bckbtn;
    private TextView signtxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        bckbtn = findViewById(R.id.backbtn);
        signtxt = findViewById(R.id.Signup_txt2);
        bckbtn.setOnClickListener(v->{
            Intent intent = new Intent(ForgotPassActivity.this, Login.class);
            startActivity(intent);
        });
        signtxt.setOnClickListener(v->{
            Intent intent = new Intent(ForgotPassActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

    }
}