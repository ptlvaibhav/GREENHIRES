package com.agriconnect.agrilink;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameField, emailField, phoneField, passwordField;
    private Spinner roleSpinner;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Repository
        userRepository = new UserRepository();

        // Bind views
        nameField = findViewById(R.id.regisnamefield);
        emailField = findViewById(R.id.regisEmailfield);
        phoneField = findViewById(R.id.regisPhonefield);
        passwordField = findViewById(R.id.regisPassfield);
        roleSpinner = findViewById(R.id.roleSpinner);
        Button submitButton = findViewById(R.id.loginbtn);

        // Set up the Spinner with the roles
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        submitButton.setOnClickListener(v -> {
            if (validateInputs()) {
                registerUser();
            }
        });
    }

    private boolean validateInputs() {
        String name = nameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String phone = phoneField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String role = roleSpinner.getSelectedItem() != null ? roleSpinner.getSelectedItem().toString() : null;

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || role == null || role.equals("Select your Role")) {
            Toast.makeText(SignUpActivity.this, "Please fill all fields and select a valid role.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void registerUser() {
        String name = nameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String phone = phoneField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String role = roleSpinner.getSelectedItem() != null ? roleSpinner.getSelectedItem().toString() : null;

        if (role == null || role.equals("Select your Role")) {
            Toast.makeText(SignUpActivity.this, "Please select a valid role.", Toast.LENGTH_SHORT).show();
            return;
        }

        userRepository.registerUser(email, password, name, phone, role, new UserRepository.OnRegistrationCompleteListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(SignUpActivity.this, "Registration successful! Redirecting to login...", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SignUpActivity.this, Login.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
