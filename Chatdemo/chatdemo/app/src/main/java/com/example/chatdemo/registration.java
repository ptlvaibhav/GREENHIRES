package com.example.chatdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class registration extends AppCompatActivity {
    private ImageView loginbut;
    private EditText rg_username, rg_email, rg_phone, rg_password, rg_repassword;
    private Spinner roleSpinner;
    private Button rg_signup;
    private CircleImageView rg_profileImg;
    private FirebaseAuth auth;
    private Uri imageURI;
    private String imageuri;
    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initializeUI();
        setupListeners();
    }

    private void initializeUI() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Establishing The Account");
        progressDialog.setCancelable(false);
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        loginbut = findViewById(R.id.loginbut);
        rg_username = findViewById(R.id.rgusername);
        rg_email = findViewById(R.id.rgemail);
        rg_phone = findViewById(R.id.rgphone);
        rg_password = findViewById(R.id.rgpassword);
        rg_repassword = findViewById(R.id.rgrepassword);
        rg_profileImg = findViewById(R.id.profilerg0);
        rg_signup = findViewById(R.id.signupbutton);
        roleSpinner = findViewById(R.id.roleSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);
    }

    private void setupListeners() {
        loginbut.setOnClickListener(v -> {
            Intent intent = new Intent(registration.this, login.class);
            startActivity(intent);
            finish();
        });

        rg_signup.setOnClickListener(v -> registerUser());

        rg_profileImg.setOnClickListener(v -> openImageChooser());
    }

    private void registerUser() {
        String namee = rg_username.getText().toString();
        String emaill = rg_email.getText().toString();
        String phone = rg_phone.getText().toString();
        String password = rg_password.getText().toString();
        String cPassword = rg_repassword.getText().toString();
        String role = roleSpinner.getSelectedItem().toString();
        String status = "Hey I'm Using This Application";

        if (validateInputs(namee, emaill, phone, password, cPassword, role)) {
            progressDialog.show(); // Show progress dialog

            // Disable the signup button to prevent multiple clicks
            rg_signup.setEnabled(false);

            auth.createUserWithEmailAndPassword(emaill, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String id = task.getResult().getUser().getUid();
                    DatabaseReference reference = database.getReference("user").child(id);
                    StorageReference storageReference = storage.getReference().child("Upload").child(id);

                    if (imageURI != null) {
                        uploadProfileImage(storageReference, reference, namee, emaill, phone, password, role, status);
                    } else {
                        imageuri = "https://firebasestorage.googleapis.com/v0/b/chatdemo2-7af4e.appspot.com/o/man.png?alt=media&token=8fa0f9be-4ae2-4138-95c6-9a8543fa6056";
                        saveUserData(reference, namee, emaill, password,phone, role, status);
                    }
                } else {
                    handleRegistrationError(task.getException().getMessage());
                }
            });
        }
    }

    private boolean validateInputs(String name, String email, String phone, String password, String cPassword, String role) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password) || TextUtils.isEmpty(cPassword) || role.equals("Select your Role")) {
            Toast.makeText(this, "Please Enter Valid Information", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!email.matches(emailPattern)) {
            rg_email.setError("Type A Valid Email Here");
            return false;
        } else if (password.length() < 6) {
            rg_password.setError("Password Must Be 6 Characters Or More");
            return false;
        } else if (!password.equals(cPassword)) {
            rg_password.setError("Passwords Don't Match");
            return false;
        } else if (phone.length() < 10) {
            rg_phone.setError("Phone Number Must Be 10 Digits");
            return false;
        }
        return true;
    }

    private void uploadProfileImage(StorageReference storageReference, DatabaseReference reference, String name, String email, String phone, String password, String role, String status) {
        storageReference.putFile(imageURI).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    imageuri = uri.toString();
                    saveUserData(reference, name, email, phone, password, role, status);
                });
            } else {
                handleUploadError(task.getException().getMessage());
            }
        });
    }

    private void saveUserData(DatabaseReference reference, String name, String email, String phone, String password, String role, String status) {
        Users users = new Users(reference.getKey(), name, email, phone, password, role, imageuri, status);
        reference.setValue(users).addOnCompleteListener(task -> {
            progressDialog.dismiss(); // Dismiss progress dialog
            if (task.isSuccessful()) {
                startActivity(new Intent(registration.this, MainActivity.class));
                finish();
            } else {
                handleRegistrationError(task.getException().getMessage());
            }
        });
    }

    private void handleUploadError(String errorMessage) {
        progressDialog.dismiss();
        rg_signup.setEnabled(true); // Re-enable the signup button
        Log.e("RegistrationError", "Image upload failed: " + errorMessage);
        Toast.makeText(this, "Image upload failed: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void handleRegistrationError(String errorMessage) {
        progressDialog.dismiss();
        rg_signup.setEnabled(true); // Re-enable the signup button
        Log.e("RegistrationError", "Registration failed: " + errorMessage);
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageURI = data.getData();
            rg_profileImg.setImageURI(imageURI);
        } else {
            Log.e("ImageSelectError", "Image selection failed or no image data received.");
        }
    }
}
