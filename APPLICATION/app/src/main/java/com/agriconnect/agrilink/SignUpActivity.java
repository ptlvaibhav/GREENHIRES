package com.agriconnect.agrilink;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etPassword, etRePassword;
    private Spinner roleSpinner;
    private Button btnSignUp;
    private ImageView backbtn;
    private TextView backlogin;
    private CircleImageView profileImg;
    private Uri imageURI;
    private String imageUri;
    private static final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseDatabase database;
    private DatabaseReference dbReference;
    private FirebaseStorage storage;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        initializeUI();
        setupListeners();
    }

    private void initializeUI() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating your account...");
        progressDialog.setCancelable(false);

        dbReference = FirebaseDatabase.getInstance().getReference().child("users");
        storage = FirebaseStorage.getInstance();
        sessionManager = new SessionManager(this);

        profileImg = findViewById(R.id.profiler);
        etName = findViewById(R.id.regisnamefield);
        etEmail = findViewById(R.id.regisEmailfield);
        etPhone = findViewById(R.id.regisPhonefield);
        etPassword = findViewById(R.id.regisPassfield);
        etRePassword = findViewById(R.id.regisConfirmPassfield);
        roleSpinner = findViewById(R.id.roleSpinner);
        btnSignUp = findViewById(R.id.signupbtn);
        backbtn = findViewById(R.id.backImage);
        backlogin = findViewById(R.id.loghere);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);
    }

    private void setupListeners() {
        backbtn.setOnClickListener(v -> navigateTo(Login.class));
        backlogin.setOnClickListener(v -> navigateTo(Login.class));
        btnSignUp.setOnClickListener(v -> registerUser());
        profileImg.setOnClickListener(v -> openImageChooser());
    }

    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(SignUpActivity.this, targetActivity);
        startActivity(intent);
        finish();
    }

    private void registerUser() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();
        String repassword = etRePassword.getText().toString();
        String role = roleSpinner.getSelectedItem().toString();
        String status = "Hey, I'm using this app";
        String userId = dbReference.push().getKey();

        if (validateInputs(userId, name, email, phone, password, repassword, role)) {
            progressDialog.show();

            // Set user reference
            DatabaseReference userReference = dbReference.child(userId);
            StorageReference storageReference = storage.getReference().child("Upload").child(userId);

            if (imageURI != null) {
                uploadProfileImage(storageReference, userId, name, email, phone, password, role, status);
            } else {
                imageUri = "https://firebasestorage.googleapis.com/v0/b/agrilink-db.appspot.com/o/user.png?alt=media&token=cf88c71d-d2f7-4c13-8292-a4d44671655f";
                saveUserData(userReference, userId, name, email, phone, password, role, status);
            }
        }
    }

    private boolean validateInputs(String userId, String name, String email, String phone, String password, String cPassword, String role) {
        if (userId == null) {
            Toast.makeText(this, "UserID not generated", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password) || TextUtils.isEmpty(cPassword) || role.equals("Select your Role")) {
            Toast.makeText(this, "Please Enter Valid Information", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!email.matches(emailPattern)) {
            etEmail.setError("Type A Valid Email Here");
            return false;
        } else if (password.length() < 6) {
            etPassword.setError("Password Must Be 6 Characters Or More");
            return false;
        } else if (!password.equals(cPassword)) {
            etPassword.setError("Passwords Don't Match");
            return false;
        } else if (phone.length() < 10) {
            etPhone.setError("Phone Number Must Be 10 Digits");
            return false;
        }
        return true;
    }

    private void uploadProfileImage(StorageReference storageReference, String userId, String name, String email, String phone, String password, String role, String status) {
        storageReference.putFile(imageURI).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    imageUri = uri.toString();
                    saveUserData(dbReference.child(userId), userId, name, email, phone, password, role, status);
                });
            } else {
                handleUploadError(task.getException().getMessage());
            }
        });
    }

    private void handleUploadError(String errorMessage) {
        progressDialog.dismiss();
        Toast.makeText(this, "Image upload failed: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void saveUserData(DatabaseReference userReference, String userId, String name, String email, String phone, String password, String role, String status) {
        fireUser user = new fireUser(userId, name, email, phone, password, role, status, imageUri);
        userReference.setValue(user).addOnCompleteListener(task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                navigateTo(Login.class);
            } else {
                handleRegistrationError(task.getException().getMessage());
            }
        });
    }

    private void handleRegistrationError(String errorMessage) {
        progressDialog.dismiss();
        Toast.makeText(this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageURI = data.getData();
            profileImg.setImageURI(imageURI);
        }
    }
}