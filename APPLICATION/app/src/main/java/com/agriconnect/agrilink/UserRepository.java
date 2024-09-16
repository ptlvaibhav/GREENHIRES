package com.agriconnect.agrilink;

import android.util.Log;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONObject;

import java.io.IOException;

public class UserRepository {

    private static final String TAG = "UserRepository";
    private static final String LAMBDA_URL = "https://tet5ob1wm3.execute-api.ap-south-1.amazonaws.com/"; // API Gateway URL

    private final OkHttpClient client = new OkHttpClient();

    /*private void registerUser() {
        String name = nameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String phone = phoneField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String role = roleSpinner.getSelectedItem() != null ? roleSpinner.getSelectedItem().toString() : null;

        if (role == null || role.equals("Select your Role")) {
            Toast.makeText(SignUpActivity.this, "Please select a valid role.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create JSON body to send to Lambda function
        JSONObject userJson = new JSONObject();
        try {
            userJson.put("name", name);
            userJson.put("email", email);
            userJson.put("phone", phone);
            userJson.put("password", password);
            userJson.put("role", role);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Use OkHttp to send the request to Lambda (via API Gateway)
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(userJson.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://your-api-gateway-url.amazonaws.com/prod/register") // Replace with your API Gateway endpoint
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Toast.makeText(SignUpActivity.this, "Registration successful! Redirecting to login...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Failed to register: " + response.message(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }


    public void getUsername(String userId, OnSearchCompleteListener listener) {
        Request request = new Request.Builder()
                .url(LAMBDA_URL + "/items/" + userId) // Adjust to the correct endpoint for fetching user
                .get() // Use GET method for retrieving user
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Error retrieving user: " + e.getMessage(), e);
                listener.onFailure("Error retrieving user: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    // Assuming response is JSON with user name
                    String username = parseUsernameFromResponse(responseBody);
                    listener.onSuccess(username);
                } else {
                    Log.e(TAG, "Error response: " + response.message());
                    listener.onFailure("Error retrieving user: " + response.message());
                }
            }
        });
    }

    private String parseUsernameFromResponse(String responseBody) {
        // Implement parsing logic here, depending on your Lambda function response format
        try {
            JSONObject json = new JSONObject(responseBody);
            return json.optString("name", "Unknown"); // Adjust based on actual JSON structure
        } catch (Exception e) {
            Log.e(TAG, "Error parsing response: " + e.getMessage(), e);
            return "Unknown";
        }
    }

    public interface OnRegistrationCompleteListener {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface OnSearchCompleteListener {
        void onSuccess(String username);
        void onFailure(String errorMessage);
    }*/
}
