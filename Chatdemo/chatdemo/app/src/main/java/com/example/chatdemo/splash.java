package com.example.chatdemo;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.*;
import android.os.*;
import android.view.WindowManager;
import android.view.animation.*;
import android.widget.*;
public class splash extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ImageView AppLogo = findViewById(R.id.SplashScreen_Logo);
        AppLogo.setImageResource(R.drawable.logo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                Intent intent = new Intent(splash.this, login.class);
                startActivity(intent);
                finish();
            }
        },3000);


    }
}