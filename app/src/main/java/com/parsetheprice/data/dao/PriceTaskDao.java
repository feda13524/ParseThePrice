package com.parsetheprice.data.dao;

import com.parsetheprice.data.entity.PriceTask;
import androidx.room.*;
import androidx.lifecycle.LiveData;
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
    // От дорогих к дешёвым

    @Query("SELECT * FROM price_tasks ORDER BY price ASC")
    LiveData<List<PriceTask>> getAllTasksFromCheap();
    // От дешёвых к дорогим

    @Query("SELECT * FROM price_tasks WHERE id = :id")
    PriceTask getTaskById(long id);
}
