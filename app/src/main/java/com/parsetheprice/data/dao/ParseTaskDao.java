package com.parsetheprice.data.dao;

import com.parsetheprice.data.entity.ParseTask;
import androidx.room.*;
import androidx.lifecycle.LiveData;
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

    @Query("SELECT * FROM parse_tasks WHERE id = :id")
    ParseTask getTaskById(long id);
}
