package com.agriconnect.agrilink;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    //Declartion of Objects
    private EditText email_Phnno, passwd;
    private Button loginbtn;
    private TextView forgetpasswd, signup;
    private SharedPreferences prefrence;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialization of the Objects
        email_Phnno = findViewById(R.id.Login_Unme);
        passwd = findViewById(R.id.Login_Upas);
        loginbtn = findViewById(R.id.loginbtn);
        forgetpasswd = findViewById(R.id.Login_forgot);
        signup = findViewById(R.id.Login_signup);
        prefrence = getSharedPreferences("User_details", MODE_PRIVATE);

        intent = new Intent(Login.this, FarmerActivity.class);  // Initialize the intent

        if (prefrence.contains("UserName") && prefrence.contains("Password")) {
            startActivity(intent);
            finish();
        }

        loginbtn.setOnClickListener(v -> {
            String userName = email_Phnno.getText().toString();
            String password = passwd.getText().toString();

            if (userName.equals("farmer@mail.com") && password.equals("farmer@123")) {
                SharedPreferences.Editor editor = prefrence.edit();
                editor.putString("UserName", userName);
                editor.putString("Password", password);
                editor.apply();

                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                intent = new Intent(Login.this, FarmerActivity.class);
                startActivity(intent);
                finish();

            } else if (userName.equals("merchant@mail.com") && password.equals("merchant@123")) {
                SharedPreferences.Editor editor = prefrence.edit();
                editor.putString("UserName", userName);
                editor.putString("Password", password);
                editor.apply();

                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                intent = new Intent(Login.this, MerchantActivity.class);
                startActivity(intent);
                finish();

            } else if (userName.equals("landlord@mail.com") && password.equals("landlord@123")) {
                SharedPreferences.Editor editor = prefrence.edit();
                editor.putString("UserName", userName);
                editor.putString("Password", password);
                editor.apply();

                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                intent = new Intent(Login.this, LandlordActivity.class);
                startActivity(intent);
                finish();

            } else if (userName.equals("industry@mail.com") && password.equals("industry@123")) {
                SharedPreferences.Editor editor = prefrence.edit();
                editor.putString("UserName", userName);
                editor.putString("Password", password);
                editor.apply();

                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                intent = new Intent(Login.this, IndustryActivity.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(getApplicationContext(), "Credentials are not valid", Toast.LENGTH_SHORT).show();
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
