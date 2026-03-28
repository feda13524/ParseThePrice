package com.parsetheprice.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.parsetheprice.data.dao.*;
import com.parsetheprice.data.database.AppDatabase;
import com.parsetheprice.data.entity.*;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class ParseRepository {
    private final ParseTaskDao parseTaskDao;
    private final PriceTaskDao priceTaskDao;
    private final LiveData<List<ParseTask>> parseTasks;
    private final LiveData<List<PriceTask>> priceTasks;
    private final ExecutorService executor;

    public ParseRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        parseTaskDao = db.parseTaskDao();
        priceTaskDao = db.priceTaskDao();

        parseTasks = parseTaskDao.getAllTasks();
        priceTasks = priceTaskDao.getAllTasks();
        executor = Executors.newSingleThreadExecutor();
    }

    // PARSE TASKS METHODS
    public LiveData<List<ParseTask>> getAllParseTasks(){
        return parseTasks;
    }

    public ParseTask getParseTaskById(long id){
        return parseTaskDao.getTaskById(id);
    }

    public void insert(ParseTask task){
        executor.execute(() -> parseTaskDao.insert(task));
    }

    public void update(ParseTask task){
        executor.execute(() -> parseTaskDao.update(task));
    }

    public void delete(ParseTask task){
        executor.execute(() -> parseTaskDao.delete((task)));
    }

    // PRICE TASKS METHODS
    public LiveData<List<PriceTask>> getAllPriceTasks(){
        return priceTasks;
    }

    public PriceTask getPriceTaskById(long id){
        return priceTaskDao.getTaskById(id);
    }

    public void insert(PriceTask task){
        executor.execute(() -> priceTaskDao.insert(task));
    }

    public void update(PriceTask task){
        executor.execute(() -> priceTaskDao.update(task));
    }

    public void delete(PriceTask task){
        executor.execute(() -> priceTaskDao.delete((task)));
    }
}
