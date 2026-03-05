package com.parsetheprice.data.entity;

import androidx.room.Entity;
@Entity(tableName = "price_tasks")
public class PriceTask extends ParseTask{
    private long price;
    private long saveAmount;

    public PriceTask(){}

    public PriceTask(String name, String link){
        super(name, link, "");
        super.setIsPrice(true);
        saveAmount = 0;
    }

    public long getPrice(){ return price; }
    public void setPrice(long price){ this.price = price; }

    public long getSaveAmount(){ return saveAmount; }
    public void setSaveAmount(long saveAmount){ this.saveAmount = saveAmount; }

    public boolean isComplete(){return saveAmount >= price; }
}
