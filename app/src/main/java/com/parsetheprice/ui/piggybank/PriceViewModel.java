package com.parsetheprice.ui.piggybank;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.parsetheprice.data.entity.PriceTask;
import com.parsetheprice.data.repository.ParseRepository;
import java.util.List;

public class PriceViewModel extends AndroidViewModel{
    private final ParseRepository repository;

    public PriceViewModel(Application application){
        super(application);
        repository = new ParseRepository(application);
    }

    public LiveData<List<PriceTask>> getAllTasks(){
        return repository.getAllPriceTasks();
    }
    public LiveData<List<PriceTask>> getAllTasksFromExpensive(){
        return repository.getAllPriceTasksFromExpensive();
    }
    public LiveData<List<PriceTask>> getAllTasksFromCheap(){
        return repository.getAllPriceTasksFromCheap();
    }
    public PriceTask getTaskById(long id){
        return repository.getPriceTaskById(id);
    }
    public void insert(String name, String link){
        PriceTask task = new PriceTask(name, link);
        repository.insert(task);
    }
    public void update(PriceTask task){
        repository.update(task);
    }
    public void delete(PriceTask task){
        repository.delete(task);
    }
}