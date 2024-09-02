package com.agriconnect.agrilink;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static final String TAG = "UserRepository";
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;

    public UserRepository(FirebaseAuth mAuth, FirebaseFirestore db) {
        this.mAuth = mAuth;
        this.db = db;
    }

    public UserRepository() {
        this(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance());
    }

    public void registerUser(String email, String password, String name, String phone, String role, OnRegistrationCompleteListener listener) {
        if (isInputInvalid(email, password, name, phone, role)) {
            listener.onFailure("Invalid input data.");
            return;
        }

        Log.d(TAG, "Registering user with email: " + email);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveUserData(name, email, phone, role, listener);
                    } else {
                        String errorMessage = "Registration failed: " + task.getException().getMessage();
                        Log.e(TAG, errorMessage);
                        listener.onFailure(errorMessage);
                    }
                });
    }

    private boolean isInputInvalid(String email, String password, String name, String phone, String role) {
        return email == null || password == null || name == null || phone == null || role == null || role.equals("Select your Role");
    }

    private void saveUserData(String name, String email, String phone, String role, OnRegistrationCompleteListener listener) {
        if (mAuth.getCurrentUser() == null) {
            String errorMessage = "User not authenticated.";
            Log.e(TAG, errorMessage);
            listener.onFailure(errorMessage);
            return;
        }

        String userId = mAuth.getCurrentUser().getUid();

        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("phone", phone);
        user.put("role", role);

        // Save user data in the main users collection
        db.collection("users")
                .document(userId)
                .set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Save user data in the role-based subcollection
                        String collectionPath = getCollectionPathForRole(role);

                        db.collection("users")
                                .document(userId)
                                .collection(collectionPath)
                                .document(userId)
                                .set(user)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Log.d(TAG, "User data saved successfully in " + collectionPath + " subcollection.");
                                        listener.onSuccess();
                                    } else {
                                        String errorMessage = "Error saving data in role-based collection: " + task1.getException().getMessage();
                                        Log.e(TAG, errorMessage);
                                        listener.onFailure(errorMessage);
                                    }
                                });
                    } else {
                        String errorMessage = "Error saving data in main users collection: " + task.getException().getMessage();
                        Log.e(TAG, errorMessage);
                        listener.onFailure(errorMessage);
                    }
                });
    }

    private String getCollectionPathForRole(String role) {
        switch (role) {
            case "Farmer":
                return "farmers";
            case "Landlord":
                return "landlords";
            case "Merchant":
                return "merchants";
            case "Industry":
                return "industries";
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    public interface OnRegistrationCompleteListener {
        void onSuccess();
        void onFailure(String errorMessage);
    }
}
