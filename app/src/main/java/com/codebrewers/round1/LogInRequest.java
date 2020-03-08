package com.codebrewers.round1;

import com.google.gson.annotations.SerializedName;

public class LogInRequest {
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;

    public LogInRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
