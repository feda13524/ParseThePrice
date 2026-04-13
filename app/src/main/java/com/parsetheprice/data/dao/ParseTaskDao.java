package com.parsetheprice.data.dao;

import androidx.room.*;
import androidx.lifecycle.LiveData;
import com.parsetheprice.data.entity.ParseTask;
import java.util.List;

@Dao
public interface ParseTaskDao{
    @Insert
    long insert(ParseTask task);

    @Update
    void update(ParseTask task);

    @Delete
    void delete(ParseTask task);

    @Query("SELECT * FROM parse_tasks ORDER BY createTime DESC")
    LiveData<List<ParseTask>> getAllTasks();

    @Query("UPDATE parse_tasks SET isExpanded = 0")
    void collapseAllTasks();

    @Query("SELECT * FROM parse_tasks WHERE id = :id")
    ParseTask getTaskById(long id);
}
