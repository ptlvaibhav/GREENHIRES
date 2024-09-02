package com.agriconnect.agrilink;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    private EditText email_Phnno, passwd;
    private Button loginbtn;
    private TextView forgetpasswd, signup;
    private SharedPreferences preference;
    private Intent intent;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

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

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loginbtn.setOnClickListener(v -> {
            try {
                String userName = email_Phnno.getText().toString().trim();
                String password = passwd.getText().toString().trim();

                if (userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter both email/phone and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(userName, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    String userId = user.getUid();
                                    db.collection("users").document(userId)
                                            .get()
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    DocumentSnapshot document = task1.getResult();
                                                    if (document != null && document.exists()) {
                                                        String role = document.getString("role");

                                                        if (role != null) {
                                                            SharedPreferences.Editor editor = preference.edit();
                                                            editor.putString("UserName", userName);
                                                            editor.putString("Password", password);
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

                                                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                                            startActivity(intent);
                                                            finish();  // Terminate the Login activity.
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "User role not found in Firestore", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "User role document does not exist", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Error fetching user data from Firestore", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed to get current user", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
}
