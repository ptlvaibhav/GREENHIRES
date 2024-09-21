package com.agriconnect.agrilink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LandlordActivity extends AppCompatActivity {
    private EditText searchBar;
    private ImageView menu,logout,scrop,gethire,aplytender, chats,setng, profile;
    private Intent intent;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord);
        //initalization of the elements
        menu = findViewById(R.id.menu);
        searchBar = findViewById(R.id.searchBar);
        logout = findViewById(R.id.btnLogOut);
        scrop = findViewById(R.id.list1);
        gethire = findViewById(R.id.list2);
        aplytender = findViewById(R.id.list3);
        chats = findViewById(R.id.list4);
        setng = findViewById(R.id.list5);
        profile = findViewById(R.id.list6);

        scrop.setOnClickListener(v -> {
            intent = new Intent(LandlordActivity.this, PostjobActivity.class);
            startActivity(intent);
        });
        gethire.setOnClickListener(v -> {
            intent = new Intent(LandlordActivity.this,GetworkerActivity.class);
            startActivity(intent);
        });
        aplytender.setOnClickListener(v -> {
            intent = new Intent(LandlordActivity.this, ApplyTenderActivity.class);
            startActivity(intent);
        });
        chats.setOnClickListener(v -> {
            intent = new Intent(LandlordActivity.this, ChatsActivity.class);
            startActivity(intent);
        });
        setng.setOnClickListener(v -> {
            intent = new Intent(LandlordActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
        profile.setOnClickListener(v -> {
            intent = new Intent(LandlordActivity.this, FprofileActivity.class);
            startActivity(intent);
        });
    }
}