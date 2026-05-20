package com.parsetheprice.ui.piggybank;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.parsetheprice.data.entity.PriceTask;
import com.parsetheprice.data.repository.ParseRepository;
import com.parsetheprice.utils.SharedPreferencesManager;

import java.util.List;

public class PriceViewModel extends AndroidViewModel{
    private final ParseRepository repository;
    private final SharedPreferencesManager prefMgr;
    private LiveData<List<PriceTask>> tasks;

    public PriceViewModel(Application application){
        super(application);
        repository = new ParseRepository(application);
        prefMgr = new SharedPreferencesManager(application);
        tasks = repository.getAllPriceTasks();
    }

    public LiveData<List<PriceTask>> getTasks(){ return tasks; }

    public PriceTask getTaskById(long id){ return repository.getPriceTaskById(id); }
    public void insert(String name, String link){
        PriceTask task = new PriceTask(name, link);
        repository.insert(task);
    }
    public void update(long id){ repository.updatePriceTask(id); }
    public void delete(long id){ repository.deletePriceTask(id); }

    // BALANCE
    public long getBalance(){ return prefMgr.loadBalance(); }
    public void setBalance(long newBalance){ prefMgr.saveBalance(newBalance); }
    public void addBalance(long sum){ setBalance(Math.max(getBalance() + sum, 0)); }

    // SORT TYPE
    public int getSortKey(){ return prefMgr.loadSortType(); }
    public void changeSortKey(){
        prefMgr.saveSortType((getSortKey() + 1) % 3);
        switch (getSortKey()) {
            case 1:
                tasks = repository.getAllPriceTasksFromExpensive();
                break;
            case 2:
                tasks = repository.getAllPriceTasksFromCheap();
                break;
            default:
                tasks = repository.getAllPriceTasks();
                break;
        }
    }
}