package com.parsetheprice.data.entity;

import androidx.room.Entity;
import static com.parsetheprice.MainActivity.getBalance;
@Entity(tableName = "price_tasks")
public class PriceTask extends ParseTask{
    private long price;

    public PriceTask(){}

    public PriceTask(String name, String link){
        super(name, link, "");
        super.setIsPrice(true);
    }

    public long getPrice(){ return price; }
    public void setPrice(long price){ this.price = price; }

    //public boolean isComplete(){return getBalance() >= price; }
}
