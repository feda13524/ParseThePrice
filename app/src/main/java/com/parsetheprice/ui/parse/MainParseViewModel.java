package com.parsetheprice.ui.parse;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.parsetheprice.data.entity.ParseTask;
import com.parsetheprice.data.entity.PriceTask;
import com.parsetheprice.data.repository.ParseRepository;
import java.util.List;

public class MainParseViewModel extends AndroidViewModel {

    public MainParseViewModel(Application application) {
        super(application);
    }

}