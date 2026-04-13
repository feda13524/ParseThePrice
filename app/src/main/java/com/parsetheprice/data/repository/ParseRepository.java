package com.parsetheprice.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.parsetheprice.data.dao.*;
import com.parsetheprice.data.entity.*;
import com.parsetheprice.data.database.AppDatabase;
import com.parsetheprice.data.api.PriceResponse;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.*;
import com.google.gson.Gson;

import static com.parsetheprice.utils.Constants.API_URL;
import static com.parsetheprice.utils.Constants.PRICE_TASK_REQUEST_ENDPOINT;

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
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        gson = new Gson();
    }

    // PARSE TASKS METHODS
    public LiveData<List<ParseTask>> getAllParseTasks(){ return parseTasks; }

    public ParseTask getParseTaskById(long id){ return parseTaskDao.getTaskById(id); }

    public void insert(ParseTask task){ executor.execute(() -> parseTaskDao.insert(task)); }

    public void deleteParseTask(long id){
        executor.execute(() -> parseTaskDao.delete(getParseTaskById(id)));
    }

    public void updateParseTask(long id){
        executor.execute(() -> parseTaskDao.update(getParseTaskById(id)));
    }

    // PRICE TASKS METHODS
    public LiveData<List<PriceTask>> getAllPriceTasks(){ return priceTasks; }
    public LiveData<List<PriceTask>> getAllPriceTasksFromCheap(){
        return priceTasksFromCheap;
    }
    public LiveData<List<PriceTask>> getAllPriceTasksFromExpensive(){
        return priceTasksFromExpensive;
    }

    public PriceTask getPriceTaskById(long id){ return priceTaskDao.getTaskById(id); }

    public void insert(PriceTask task){ executor.execute(() -> priceTaskDao.insert(task)); }

    public void deletePriceTask(long id){
        executor.execute(() -> priceTaskDao.delete(priceTaskDao.getTaskById(id)));
    }

    public void updatePriceTask(long id) {
        executor.execute(() -> {
            PriceTask task = getPriceTaskById(id);
            task.setStatus('?');
            priceTaskDao.update(task);
            task.setStatus('-');

            try {
                String jsonRequest =
                        "{\"url\":\"" + task.getLink() + "\"}";

                RequestBody body = RequestBody.create(
                        jsonRequest,
                        MediaType.parse("application/json")
                );

                Request request = new Request.Builder()
                        .url(API_URL + PRICE_TASK_REQUEST_ENDPOINT)
                        .post(body)
                        .build();

                try (Response response = httpClient.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        String jsonResponse = response.body().string();
                        PriceResponse priceResponse = gson.fromJson(jsonResponse, PriceResponse.class);

                        if (priceResponse.isSuccessful()){
                            task.setStatus('+');
                            task.setPrice(priceResponse.getPrice());
                        }
                    }
                }
            } catch (Exception ignored){}

            task.updateTime();
            priceTaskDao.update(task);
        });
    }
}
