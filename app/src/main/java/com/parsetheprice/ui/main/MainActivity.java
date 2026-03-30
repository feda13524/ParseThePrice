package com.parsetheprice.ui.main;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import androidx.lifecycle.ViewModelProvider;

import com.parsetheprice.R;
import com.parsetheprice.data.entity.ParseTask;

import com.parsetheprice.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private float startY;
    private MainViewModel viewModel;
    private List<ParseTask> taskList = new ArrayList<>();
    private GestureDetector gestureDetector;

    private static SharedPreferencesManager prefMgr;
    private static long balance;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        gestureDetector = new GestureDetector(this, new SwipeGestureListener(this));
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Бары - батарея и тд для сохранения отступов
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}