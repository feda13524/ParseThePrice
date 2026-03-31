package com.parsetheprice.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.gson.Gson;
import com.parsetheprice.data.api.PriceResponse;
import com.parsetheprice.data.dao.*;
import com.parsetheprice.data.database.AppDatabase;
import com.parsetheprice.data.entity.*;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import okhttp3.*;

public class ParseRepository {
    private final ParseTaskDao parseTaskDao;
    private final PriceTaskDao priceTaskDao;
    private final LiveData<List<ParseTask>> parseTasks;
    private final LiveData<List<PriceTask>> priceTasks;
    private final LiveData<List<PriceTask>> priceTasksFromCheap;
    private final LiveData<List<PriceTask>> priceTasksFromExpensive;
    private final ExecutorService executor;
    private final OkHttpClient httpClient;
    private final Gson gson;

    public ParseRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        parseTaskDao = db.parseTaskDao();
        priceTaskDao = db.priceTaskDao();

        parseTasks = parseTaskDao.getAllTasks();
        priceTasks = priceTaskDao.getAllTasks();
        priceTasksFromCheap = priceTaskDao.getAllTasksFromCheap();
        priceTasksFromExpensive = priceTaskDao.getAllTasksFromExpensive();

        executor = Executors.newSingleThreadExecutor();
        httpClient = new OkHttpClient();
        gson = new Gson();
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

    public LiveData<List<PriceTask>> getAllPriceTasksFromCheap(){
        return priceTasksFromCheap;
    }

    public LiveData<List<PriceTask>> getAllPriceTasksFromExpensive(){
        return priceTasksFromExpensive;
    }

    public PriceTask getPriceTaskById(long id){
        return priceTaskDao.getTaskById(id);
    }

    public void insert(PriceTask task){
        executor.execute(() -> priceTaskDao.insert(task));
    }
    public void delete(long id){
        executor.execute(() -> priceTaskDao.delete(priceTaskDao.getTaskById(id)));
    }
    public void update(long id) {
        executor.execute(() -> {
            PriceTask task = getPriceTaskById(id);
            task.setStatus('?');
            priceTaskDao.update(task);
            try {
                String json = "{\"url\":\"" + task.getLink() + "\"}";
                RequestBody body = RequestBody.create(
                        json,
                        MediaType.parse("application/json")
                );

                Request request = new Request.Builder()
                        .url("https://api.fedosik.ru/ptp/price")
                        .post(body)
                        .build();

                try (Response response = httpClient.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        String jsonResponse = response.body().string();
                        PriceResponse priceResponse = gson.fromJson(jsonResponse, PriceResponse.class);

                        if (priceResponse.isResult()){
                            task.setStatus('+');
                            task.setPrice((long) priceResponse.getPrice());
                        } else { task.setStatus('-'); }
                    } else { task.setStatus('-'); }
                    task.updateTime();
                    priceTaskDao.update(task);
                }
            } catch (Exception e) {
                task.setStatus('-');
                task.updateTime();
                priceTaskDao.update(task);
            }
        });
    }
}
