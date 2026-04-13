package com.parsetheprice.data.api;

import com.google.gson.annotations.SerializedName;

public class PriceResponse {
    @SerializedName("price")
    private double price;
    @SerializedName("success")
    private boolean success;

    public double getPrice() { return price; }
    public boolean isSuccess() { return success; }
}