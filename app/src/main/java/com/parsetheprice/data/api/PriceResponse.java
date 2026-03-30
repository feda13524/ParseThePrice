package com.parsetheprice.data.api;

import com.google.gson.annotations.SerializedName;

public class PriceResponse {
    @SerializedName("price")
    private double price;
    @SerializedName("result")
    private boolean result;

    public double getPrice() { return price; }
    public boolean isResult() { return result; }
}