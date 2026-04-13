package com.parsetheprice.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.parsetheprice.utils.Constants.DATE_PATTERN;
import static com.parsetheprice.utils.Constants.PARSE_TASK_TABLE_NAME;

@Entity(tableName = PARSE_TASK_TABLE_NAME)
public class ParseTask{
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String link;
    private char status;
    private long createTime;
    private long lastTime;
    private String message;
    private String parsingResult;
    private boolean isExpanded;

    public ParseTask(){}

    public ParseTask(String name, String link, String message){
        this.name = name;
        this.link = formatLink(link);
        this.status = '?';
        this.createTime = System.currentTimeMillis();
        this.lastTime = this.createTime;
        this.message = message;
    }

    private String formatLink(String link) {
        if (!link.startsWith("http://") && !link.startsWith("https://")) {
            return "https://" + link;
        }
        return link;
    }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        return sdf.format(new Date(lastTime));
    }
    public void updateTime() { this.lastTime = System.currentTimeMillis(); }

    public long getId(){ return id; }
    public void setId(long id){ this.id = id; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getLink(){ return link; }
    public void setLink(String link){ this.link = formatLink(link); }

    public char getStatus(){ return status; }
    public void setStatus(char status){ this.status = status; }

    public long getCreateTime(){ return createTime; }
    public void setCreateTime(long createTime){ this.createTime = createTime; }

    public long getLastTime(){ return lastTime; }
    public void setLastTime(long lastTime){ this.lastTime = lastTime; }

    public String getMessage(){ return message; }
    public void setMessage(String message){ this.message = message; }

    public String getParsingResult(){ return parsingResult; }
    public void setParsingResult(String parsingResult){ this.parsingResult = parsingResult; }

    public boolean getIsExpanded() { return isExpanded; }
    public void setIsExpanded(boolean isExpanded) { this.isExpanded = isExpanded; }
}
