package com.parsetheprice.data.entity;

import androidx.room.Entity;
import static com.parsetheprice.utils.Constants.PRICE_TASK_TABLE_NAME;

@Entity(tableName = PRICE_TASK_TABLE_NAME)
public class PriceTask extends ParseTask{
    private double price;

    public PriceTask(){}

    public PriceTask(String name, String link){ super(name, link, ""); }

    public double getPrice(){ return price; }
    public void setPrice(double price){ this.price = price; }
}
