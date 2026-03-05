package com.parsetheprice.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.net.URL;

public class ParseTask {
    private long id;
    private String name;
    private URL link;
    private char status;
    // + - ?
    private long lastTime;
    private boolean isPrice;
    private String message;
    private String parseData;

    public ParseTask(String name, URL link, String message){
        this.name = name;
        this.link = link;
        this.status = '?';
        isPrice = false;
        this.message = message;
    }

    public long getId(){ return id; }
    public void setId(long id){ this.id = id; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public URL getLink(){ return link; }
    public void setLink(URL link){ this.link = link; }

    public char getStatus(){ return status; }
    public void setStatus(char status){ this.status = status; }

    public long getLastTime(){ return lastTime; }
    public void setLastTime(long lastTime){ this.lastTime = lastTime; }

    public boolean getIsPrice(){ return isPrice; }
    public void setIsPrice(boolean isPrice){ this.isPrice = isPrice; }

    public String getMessage(){ return message; }
    public void setMessage(String message){ this.message = message; }

    public String getParseData(){ return parseData; }
    public void setParseData(String parseData){ this.parseData = parseData; }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date(lastTime));
    }
}

/*
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "requests")
public class RequestEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
*/