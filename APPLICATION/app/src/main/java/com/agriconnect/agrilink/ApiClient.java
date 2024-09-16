package com.agriconnect.agrilink;
//
//import okhttp3.*;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//
public class ApiClient {
//
//    //private final OkHttpClient client = new OkHttpClient();
//    private final String baseUrl = "https://tet5ob1wm3.execute-api.ap-south-1.amazonaws.com/"; // Replace with your API Gateway URL
//
//    // Method to register a user
//    public void registerUser(String id, String email, String name, String phone, String role, Callback callback) {
//        String url = baseUrl + "/items"; // Adjust endpoint as needed
//
//        JSONObject json = new JSONObject();
//        try {
//            json.put("id", id);
//            json.put("email", email);
//            json.put("name", name);
//            json.put("phone", phone);
//            json.put("role", role);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            // Notify callback of failure if JSON creation fails
//            callback.onFailure(null, new IOException("Failed to create JSON object"));
//            return;
//        }
//
//        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body) // Use POST for registration
//                .build();
//
//        client.newCall(request).enqueue(callback);
//    }
//
//    // Method to get user data by ID
//    public void getUser(String id, Callback callback) {
//        String url = baseUrl + "/items/" + id; // Adjust endpoint as needed
//
//        Request request = new Request.Builder()
//                .url(url)
//                .get() // Use GET to fetch user data
//                .build();
//
//        client.newCall(request).enqueue(callback);
//    }
//
//    // Method to delete a user by ID
//    public void deleteUser(String id, Callback callback) {
//        String url = baseUrl + "/items/" + id; // Adjust endpoint as needed
//
//        Request request = new Request.Builder()
//                .url(url)
//                .delete() // Use DELETE to remove user data
//                .build();
//
//        client.newCall(request).enqueue(callback);
//    }
//
//    // Method to search users
//    public void searchUsers(String query, Callback callback) {
//        String url = baseUrl + "/search/users?query=" + query; // Adjust endpoint as needed
//
//        Request request = new Request.Builder()
//                .url(url)
//                .get() // Use GET for searching users
//                .build();
//
//        client.newCall(request).enqueue(callback);
//    }
//
//    // Method to search tenders
//    public void searchTenders(Callback callback) {
//        String url = baseUrl + "/search/tenders"; // Adjust endpoint as needed
//
//        Request request = new Request.Builder()
//                .url(url)
//                .get() // Use GET for searching tenders
//                .build();
//
//        client.newCall(request).enqueue(callback);
//    }
//
//    // Add more methods for other operations as needed
}
