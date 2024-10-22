package com.agriconnect.agrilink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "user_details";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_ROLE = "role";
    private static final String IS_LOGGED_IN ="isLoggedIn";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Save user session
    public void createLoginSession(String userId, String role){
        editor.putBoolean(IS_LOGGED_IN,true);
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_ROLE, role);
        editor.commit();
    }

    // Check if user is logged in
    public boolean isLoggedIn(){
        return pref.contains(KEY_USER_ID);
    }

    // Get user ID
    public String getUserId(){
        return pref.getString(KEY_USER_ID, null);
    }

    // Get user role
    public String getRole(){
        return pref.getString(KEY_ROLE,null);
    }

    // Logout user
    public void logout(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
