package com.agriconnect.agrilink;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FarmerPresenter {
    private FarmerView view;
    //private ApiClient apiClient;

    /*public FarmerPresenter(FarmerView view, ApiClient apiClient) {
        //this.view = view;
        //this.apiClient = apiClient;
    }

    public void loadUserProfile(String userId) {
        apiClient.getUser(userId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                view.showError("Error loading user profile: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject json = new JSONObject(responseBody);
                        String username = json.optString("name", "Unknown"); // Adjust field name if needed
                        view.showUserProfile(username);
                    } catch (JSONException e) {
                        view.showError("Error parsing user profile data: " + e.getMessage());
                    }
                } else {
                    view.showError("Error response: " + response.message());
                }
            }
        });
    }

    public void searchUsers(String query) {
        apiClient.searchUsers(query, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                view.showError("Error searching users: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONArray jsonArray = new JSONArray(responseBody);
                        List<String> results = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            results.add(jsonArray.getString(i));
                        }
                        view.showUserList(results);
                    } catch (JSONException e) {
                        view.showError("Error parsing user list: " + e.getMessage());
                    }
                } else {
                    view.showError("Error response: " + response.message());
                }
            }
        });
    }

    public void searchTenders() {
        apiClient.searchTenders(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                view.showError("Error searching tenders: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONArray jsonArray = new JSONArray(responseBody);
                        List<String> results = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            results.add(jsonArray.getString(i));
                        }
                        view.showTenderList(results);
                    } catch (JSONException e) {
                        view.showError("Error parsing tender list: " + e.getMessage());
                    }
                } else {
                    view.showError("Error response: " + response.message());
                }
            }
        });
    }

    /*public void loadPastChats(String userId) {
        apiClient.getPastChats(userId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                view.showError("Error loading past chats: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONArray jsonArray = new JSONArray(responseBody);
                        List<String> results = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            results.add(jsonArray.getString(i));
                        }
                        view.showPastChats(results);
                    } catch (JSONException e) {
                        view.showError("Error parsing past chats: " + e.getMessage());
                    }
                } else {
                    view.showError("Error response: " + response.message());
                }
            }
        });
    }*/
}
