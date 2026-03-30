package com.parsetheprice.ui.main;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.parsetheprice.data.entity.ParseTask;
import com.parsetheprice.data.entity.PriceTask;
import com.parsetheprice.data.repository.ParseRepository;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final ParseRepository repository;
    private final LiveData<List<ParseTask>> allParseTasks;
    private final LiveData<List<PriceTask>> allPriceTasks;

    public MainViewModel(Application application) {
        super(application);
        repository = new ParseRepository(application);
        allParseTasks = repository.getAllParseTasks();
        allPriceTasks = repository.getAllPriceTasks();
    }

    // ========== Получение данных ==========

    public LiveData<List<ParseTask>> getAllParseTasks() {
        return allParseTasks;
    }

    public LiveData<List<PriceTask>> getAllPriceTasks() {
        return allPriceTasks;
    }

    public ParseTask getParseTaskById(long id) {
        return repository.getParseTaskById(id);
    }

    public PriceTask getPriceTaskById(long id) {
        return repository.getPriceTaskById(id);
    }

    // ========== Операции с ParseTask ==========

    public void insertParseTask(String name, String link, String userText) {
        ParseTask task = new ParseTask(name, link, userText);
        repository.insert(task);
    }

    public void updateParseTask(ParseTask task) {
        repository.update(task);
    }

    public void deleteParseTask(ParseTask task) {
        repository.delete(task);
    }

    // ========== Операции с PriceTask ==========

    public void insertPriceTask(String name, String link, long price) {
        PriceTask task = new PriceTask(name, link);
        task.setPrice(price);
        repository.insert(task);
    }

    public void updatePriceTask(PriceTask task) {
        repository.update(task);
    }

    public void deletePriceTask(PriceTask task) {
        repository.delete(task);
    }
}