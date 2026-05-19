package com.parsetheprice.ui.parsing;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.parsetheprice.data.entity.ParseTask;
import com.parsetheprice.data.repository.ParseRepository;
import java.util.List;

public class ParseViewModel extends AndroidViewModel{
    private final ParseRepository repository;

    public ParseViewModel(Application application){
        super(application);
        repository = new ParseRepository(application);
    }

    public LiveData<List<ParseTask>> getAllTasks(){
        return repository.getAllParseTasks();
    }
    public ParseTask getTaskById(long id){
        return repository.getParseTaskById(id);
    }
    public void insert(String name, String link, String message){
        ParseTask task = new ParseTask(name, link, message);
        repository.insert(task);
        repository.updateParseTask(task.getId());
    }
    public void update(long id){ repository.updateParseTask(id); }
    public void delete(long id){ repository.deleteParseTask(id); }
}