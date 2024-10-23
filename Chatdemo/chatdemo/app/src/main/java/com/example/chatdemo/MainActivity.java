package com.example.chatdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences; // Import SharedPreferences
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String PREFS_NAME = "UserPrefs"; // SharedPreferences name
    private static final String KEY_USER_NAME = "username"; // Key for username

    private FirebaseAuth auth;
    private RecyclerView mainUserRecyclerView;
    private UserAdpter adapter; // Corrected the typo from 'UserAdpter' to 'UserAdapter'
    private FirebaseDatabase database;
    private ArrayList<Users> usersArrayList;
    private ImageView imgLogout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Safely attempt to hide the ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
            Log.d(TAG, "ActionBar hidden successfully.");
        } else {
            Log.w(TAG, "ActionBar is null. Cannot hide.");
        }

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        imgLogout = findViewById(R.id.logoutimg);
        mainUserRecyclerView = findViewById(R.id.mainUserRecyclerView);

        usersArrayList = new ArrayList<>();
        mainUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdpter(MainActivity.this, usersArrayList);
        mainUserRecyclerView.setAdapter(adapter);

        DatabaseReference reference = database.getReference().child("user");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersArrayList.clear(); // Clear the list to prevent duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users users = dataSnapshot.getValue(Users.class);
                    if (users != null) { // Check for null to prevent potential issues
                        usersArrayList.add(users);
                    }
                }
                adapter.notifyDataSetChanged();
                Log.d(TAG, "Users fetched and adapter notified.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to fetch users: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onCancelled: ", error.toException());
            }
        });

        // Setup Logout Functionality
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });

        // Check if User is Authenticated
        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, login.class); // Ensure 'LoginActivity' exists
            startActivity(intent);
            finish();
            Log.d(TAG, "No authenticated user. Redirecting to LoginActivity.");
        }

        // Retrieve and display the saved username
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString(KEY_USER_NAME, null);
        if (savedUsername != null) {
            // You can use savedUsername wherever needed, e.g., display it in a TextView
            Log.d(TAG, "Retrieved username: " + savedUsername);
        }
    }

    private void showLogoutDialog() {
        Dialog dialog = new Dialog(MainActivity.this, R.style.dialoge);
        dialog.setContentView(R.layout.dialog_layout);
        Button noButton, yesButton;
        yesButton = dialog.findViewById(R.id.yesbnt);
        noButton = dialog.findViewById(R.id.nobnt);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, login.class); // Ensure 'LoginActivity' exists
                startActivity(intent);
                finish();
                Log.d(TAG, "User signed out and redirected to LoginActivity.");
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Log.d(TAG, "Logout dialog dismissed.");
            }
        });
        dialog.show();
        Log.d(TAG, "Logout dialog displayed.");
    }

    // Save the username to SharedPreferences
    public void saveUsername(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_NAME, username);
        editor.apply();
        Log.d(TAG, "Username saved: " + username);
    }

    // Handle the result from camera intent if needed
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            // Handle the captured image if necessary
            Bundle extras = data.getExtras();
            if (extras != null) {
                //For example, set the image to an ImageView
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                //imageView.setImageBitmap(imageBitmap);
                Log.d(TAG, "Image captured successfully.");
            }
        } else {
            Log.w(TAG, "Image capture canceled or failed.");
        }
    }
}
