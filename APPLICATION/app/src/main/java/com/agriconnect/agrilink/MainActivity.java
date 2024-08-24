package com.agriconnect.agrilink;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.ImageView;



public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Setting the FLAGs
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        // Objects Creation
        ImageView AppLogo = findViewById(R.id.SplashScreen_Logo);
        AppLogo.setImageResource(R.drawable.logo);

        //(Use of)Handler : to Schedule the code that should run in Future
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        },5000);


    }
}