package com.agriconnect.agrilink;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText email_Phnno, passwd;
    private Button loginbtn;
    private TextView forgetpasswd, signup;
    private Intent intent;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private SessionManager sessionManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        email_Phnno = findViewById(R.id.Login_Unme);
        passwd = findViewById(R.id.Login_Upas);
        loginbtn = findViewById(R.id.loginbtn);
        forgetpasswd = findViewById(R.id.Login_forgot);
        signup = findViewById(R.id.Login_signup);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        sessionManager = new SessionManager(this);

        // Check if user is already logged in
        if (sessionManager.isLoggedIn()) {
            String userId = sessionManager.getUserId();
            String role = sessionManager.getRole();
            navigateToRoleSpecificActivity(userId, role);
        }

        loginbtn.setOnClickListener(v -> loginUser());
        forgetpasswd.setOnClickListener(v -> {
            intent = new Intent(Login.this, ForgotPassActivity.class);
            startActivity(intent);
        });

        signup.setOnClickListener(v -> {
            intent = new Intent(Login.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String userName = email_Phnno.getText().toString().trim();
        String password = passwd.getText().toString().trim();

        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        databaseReference.orderByChild("email").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressDialog.dismiss();
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        fireUser user = userSnapshot.getValue(fireUser.class);
                        if (user != null) {
                            if (user.getPassword().equals(password)) {
                                sessionManager.createLoginSession(userSnapshot.getKey(), user.getRole());
                                navigateToRoleSpecificActivity(userSnapshot.getKey(), user.getRole());
                            } else {
                                Toast.makeText(Login.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(Login.this, "Email not registered.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(Login.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToRoleSpecificActivity(String userId, String role) {
        Intent intent;
        switch (role) {
            case "Farmer":
                intent = new Intent(Login.this, FarmerActivity.class);
                break;
            case "Landlord":
                intent = new Intent(Login.this, LandlordActivity.class);
                break;
            case "Merchant":
                intent = new Intent(Login.this, MerchantActivity.class);
                break;
            case "Industry":
                intent = new Intent(Login.this, IndustryActivity.class);
                break;
            default:
                Toast.makeText(Login.this, "Invalid user role", Toast.LENGTH_SHORT).show();
                return;
        }
        startActivity(intent);
        finish();
    }
}




    /*private void login(String userName, String password) {
        AirTableApiService service = RetrofitClient.getClient().create(AirTableApiService.class);
        String filter = "AND({Email} = '" + userName + "', {Password} = '" + password + "')";
        Call<AirtableResponse> call = service.getUser(filter);

        call.enqueue(new Callback<AirtableResponse>() {
            @Override
            public void onResponse(Call<AirtableResponse> call, Response<AirtableResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("LoginResponse", response.body().toString());
                    List<AirtableResponse.UserRecord> records = response.body().getRecords();
                    if (!records.isEmpty()) {
                        AirtableResponse.Fields fields = records.get(0).getFields();
                        String role = fields.getRole();

                        if (role == null || role.isEmpty()) {
                            Toast.makeText(Login.this, "User role not found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        SharedPreferences.Editor editor = preference.edit();
                        editor.putString("UserName", userName);
                        //editor.putString("Password", password);
                        editor.putString("Role", role);
                        editor.apply();

                        switch (role) {
                            case "Farmer":
                                intent = new Intent(Login.this, FarmerActivity.class);
                                break;
                            case "Merchant":
                                intent = new Intent(Login.this, MerchantActivity.class);
                                break;
                            case "Landlord":
                                intent = new Intent(Login.this, LandlordActivity.class);
                                break;
                            case "Industry":
                                intent = new Intent(Login.this, IndustryActivity.class);
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Unknown user role", Toast.LENGTH_SHORT).show();
                                return;
                        }

                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(Login.this, "Invalid credentials or role not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AirtableResponse> call, Throwable t) {
                // Log the error message for debugging
                Log.e("LoginError", "Login failed: " + t.getMessage(), t);

                // Show the error message in a toast
                Toast.makeText(Login.this, "Login failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }*/