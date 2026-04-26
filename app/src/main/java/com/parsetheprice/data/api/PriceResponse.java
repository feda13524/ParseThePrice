package com.parsetheprice.data.api;

import com.google.gson.annotations.SerializedName;

public class PriceResponse {
    @SerializedName("price")
    private double price;
    @SerializedName("successful")
    private boolean successful;

    public double getPrice() { return price; }
    public boolean isSuccessful() { return successful; }
}