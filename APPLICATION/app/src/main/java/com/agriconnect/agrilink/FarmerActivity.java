package com.agriconnect.agrilink;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FarmerActivity extends AppCompatActivity //implements FarmerView
{
    private EditText searchBar;
    private ListView userListView;
    private ListView tenderListView;
    private ListView chatListView;
    private ArrayAdapter<String> userAdapter;
    private ArrayAdapter<String> tenderAdapter;
    private ArrayAdapter<String> chatAdapter;
    private FarmerPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);

        // Initialize views
        /*searchBar = findViewById(R.id.searchBar);
        userListView = findViewById(R.id.userListView);
        tenderListView = findViewById(R.id.tenderListView);
        chatListView = findViewById(R.id.chatListView);
        TextView result = findViewById(R.id.resultView);
        Button btnLogOut = findViewById(R.id.btnLogOut);

        // Initialize presenter with existing UserRepository
        presenter = new FarmerPresenter(this, new ApiClient());

        // Set up adapters
        userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        tenderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        chatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());

        userListView.setAdapter(userAdapter);
        tenderListView.setAdapter(tenderAdapter);
        chatListView.setAdapter(chatAdapter);

        // Set up search functionality
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.searchUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Load profile and past chats
        //presenter.loadUserProfile();
        //presenter.loadPastChats(); // Uncomment if needed

        // Button click listener for logout
        btnLogOut.setOnClickListener(v -> {
            getSharedPreferences("User_details", MODE_PRIVATE).edit().clear().apply();
            Intent intent = new Intent(FarmerActivity.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void showUserProfile(String username) {
        TextView result = findViewById(R.id.resultView);
        result.setText("Hello, " + username);
    }

    @Override
    public void showUserList(List<String> users) {
        userAdapter.clear();
        userAdapter.addAll(users);
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTenderList(List<String> tenders) {
        tenderAdapter.clear();
        tenderAdapter.addAll(tenders);
        tenderAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        // Optionally, you can show the error to the user, e.g., using a Toast
        // Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }*/
    }
}