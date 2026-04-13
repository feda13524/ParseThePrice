package com.parsetheprice.data.dao;

import androidx.room.*;
import androidx.lifecycle.LiveData;
import com.parsetheprice.data.entity.PriceTask;
import java.util.List;

@Dao
public interface PriceTaskDao{
    @Insert
    long insert(PriceTask task);

    @Update
    void update(PriceTask task);

    @Delete
    void delete(PriceTask task);

    @Query("SELECT * FROM price_tasks ORDER BY createTime DESC")
    LiveData<List<PriceTask>> getAllTasks();

    @Query("SELECT * FROM price_tasks ORDER BY price DESC")
    LiveData<List<PriceTask>> getAllTasksFromExpensive();

    @Query("SELECT * FROM price_tasks ORDER BY price ASC")
    LiveData<List<PriceTask>> getAllTasksFromCheap();

    @Query("SELECT * FROM price_tasks WHERE id = :id")
    PriceTask getTaskById(long id);
}
