package com.agriconnect.agrilink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText email_Phnno, passwd;
    private Button loginbtn;
    private TextView forgetpasswd, signup;
    private SharedPreferences preference;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_Phnno = findViewById(R.id.Login_Unme);
        passwd = findViewById(R.id.Login_Upas);
        loginbtn = findViewById(R.id.loginbtn);
        forgetpasswd = findViewById(R.id.Login_forgot);
        signup = findViewById(R.id.Login_signup);

        preference = getSharedPreferences("User_details", MODE_PRIVATE);

        loginbtn.setOnClickListener(v -> {
            String userName = email_Phnno.getText().toString().trim();
            String password = passwd.getText().toString().trim();

            if (userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter both email and password", Toast.LENGTH_SHORT).show();
            } else {
                login(userName, password);
            }
        });

        forgetpasswd.setOnClickListener(v -> {
            intent = new Intent(Login.this, ForgotPassActivity.class);
            startActivity(intent);
        });

        signup.setOnClickListener(v -> {
            intent = new Intent(Login.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void login(String userName, String password) {
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
                        editor.putString("Password", password);
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
    }
}
