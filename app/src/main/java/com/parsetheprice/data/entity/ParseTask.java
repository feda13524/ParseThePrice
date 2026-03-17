package com.parsetheprice.data.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "parse_tasks")
public class ParseTask{
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String link;
    private char status; // +parsed -error ?parsing
    private long lastTime;
    private boolean isPrice;
    private String message;
    private String parseData;
    private boolean isExpanded;

    public ParseTask(String name, String link, String message){
        this.name = name;
        this.link = formatLink(link);
        this.status = '?';
        this.lastTime = System.currentTimeMillis();
        this.isPrice = false;
        this.message = message;
    }
    private String formatLink(String link) {
        if (!link.startsWith("http://") && !link.startsWith("https://")) {
            return "https://" + link;
        }
        return link;
    }

    public long getId(){ return id; }
    public void setId(long id){ this.id = id; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getLink(){ return link; }
    public void setLink(String link){ this.link = formatLink(link); }

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
    public boolean isExpanded() { return isExpanded; }
    public void setExpanded(boolean expanded) { isExpanded = expanded; }
    public void updateTimestamp() {
        this.lastTime = System.currentTimeMillis();
    }
    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm / dd.MM.yyyy", Locale.getDefault());
        return sdf.format(new Date(lastTime));
    }
}
