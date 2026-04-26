package com.parsetheprice.data.api;

import com.google.gson.annotations.SerializedName;

public class ParseResponse {
    @SerializedName("result")
    private String result;
    @SerializedName("successful")
    private boolean successful;

    public String getResult() { return result; }
    public boolean isSuccessful() { return successful; }
}