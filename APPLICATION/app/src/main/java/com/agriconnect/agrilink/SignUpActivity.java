package com.agriconnect.agrilink;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etPassword;
    private Spinner roleSpinner;
    private Button btnSignUp;
    private ImageView backbtn;
    private TextView backlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = findViewById(R.id.regisnamefield);
        etEmail = findViewById(R.id.regisEmailfield);
        etPhone = findViewById(R.id.regisPhonefield);
        etPassword = findViewById(R.id.regisPassfield);
        roleSpinner = findViewById(R.id.roleSpinner);
        btnSignUp = findViewById(R.id.loginbtn);
        backbtn = findViewById(R.id.backImage);
        backlogin = findViewById(R.id.loghere);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        backbtn.setOnClickListener(v->{
            Intent intent = new Intent(SignUpActivity.this, Login.class);
            startActivity(intent);
        });
        backlogin.setOnClickListener(v->{
            Intent intent = new Intent(SignUpActivity.this, Login.class);
            startActivity(intent);
        });
        btnSignUp.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();
            String password = etPassword.getText().toString();
            String role = roleSpinner.getSelectedItem().toString();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || role.equals("Select your Role")) {
                Toast.makeText(SignUpActivity.this, "Please fill all fields and select a valid role", Toast.LENGTH_SHORT).show();
            } else {
                signUp(email, name, phone, password, role);
            }
        });
    }

    private void signUp(String email, String name, String phone, String password, String role) {
        // Create the fields for the user
        AirtableUser.Record.Fields fields = new AirtableUser.Record.Fields(email, name, phone, password, role);
        AirtableUser.Record record = new AirtableUser.Record(fields);

        // Wrap the record in a list (in case you want to send multiple users at once)
        List<AirtableUser.Record> records = new ArrayList<>();
        records.add(record);

        // Create the AirtableUser object with the records list
        AirtableUser user = new AirtableUser(records);

        // Create the Retrofit service
        AirTableApiService service = RetrofitClient.getClient().create(AirTableApiService.class);
        Call<AirtableResponse> call = service.createUser(user);

        // Make the API call to store user data
        call.enqueue(new Callback<AirtableResponse>() {
            @Override
            public void onResponse(Call<AirtableResponse> call, Response<AirtableResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Success", "User created with records: " + response.body().getRecords());
                    Toast.makeText(SignUpActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();

                    // Navigate to login page
                    Intent intent = new Intent(SignUpActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        Log.e("Error", "Response not successful: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(SignUpActivity.this, "Failed to create user", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<AirtableResponse> call, Throwable t) {
                Log.e("Error", "API call failed: " + t.getMessage(), t);
                Toast.makeText(SignUpActivity.this, "Sign-up failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
