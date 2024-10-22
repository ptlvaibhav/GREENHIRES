package com.agriconnect.agrilink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FarmerActivity extends AppCompatActivity
{
    private EditText searchBar;
    private ImageView menu,logout,scrop,gethire,aplytender, chats,setng, profile;
    private Intent intent;
    private SharedPreferences sp;
    private TextView helotxt;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);
        //initalization of the elements
        menu = findViewById(R.id.menu);
//        searchBar = findViewById(R.id.searchBar);
        logout = findViewById(R.id.log_out);
        helotxt = findViewById(R.id.hellotxt);
        scrop = findViewById(R.id.list1);
        gethire = findViewById(R.id.list2);
        aplytender = findViewById(R.id.list3);
        chats = findViewById(R.id.list4);
        setng = findViewById(R.id.list5);
        profile = findViewById(R.id.list6);
        sessionManager = new SessionManager(this);

        String username = getIntent().getStringExtra("username");
        if (username != null)
            helotxt.setText("Hello, "+ username);

        logout.setOnClickListener( v -> sessionManager.logout());
        scrop.setOnClickListener(v -> {
            intent = new Intent(FarmerActivity.this, SellCropActivity.class);
            startActivity(intent);
        });
        gethire.setOnClickListener(v -> {
            intent = new Intent(FarmerActivity.this,GetHiredActivity.class);
            startActivity(intent);
        });
        aplytender.setOnClickListener(v -> {
            intent = new Intent(FarmerActivity.this, ApplyTenderActivity.class);
            startActivity(intent);
        });
        chats.setOnClickListener(v -> {
            intent = new Intent(FarmerActivity.this, ChatsActivity.class);
            startActivity(intent);
        });
        setng.setOnClickListener(v -> {
            intent = new Intent(FarmerActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
        profile.setOnClickListener(v -> {
            intent = new Intent(FarmerActivity.this, FprofileActivity.class);
            startActivity(intent);
        });

    }
}