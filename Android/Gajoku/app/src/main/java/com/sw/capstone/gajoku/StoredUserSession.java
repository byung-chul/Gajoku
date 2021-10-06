package com.sw.capstone.gajoku;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class StoredUserSession {

    SharedPreferences sp;
    Context context;

    StoredUserSession(Context context){
        this.context = context;
        this.sp = this.context.getSharedPreferences("User", MODE_PRIVATE);
    }

    public String getUserSession(){
        String userId = sp.getString("id", "");
        return userId;
    }

    public void storeUserSession(String userId){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("id", userId);
        editor.apply();
    }

    public void clearUserSession(){
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
