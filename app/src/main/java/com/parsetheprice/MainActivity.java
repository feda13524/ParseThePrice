package com.parsetheprice;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

import com.parsetheprice.utils.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity {
    private float startY;
    private GestureDetector gestureDetector;

    private SharedPreferencesManager prefMgr;
    private static long balance;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gestureDetector = new GestureDetector(this, new SwipeGestureListener(this));

        // Загрузка информации о балансе
        prefMgr = new SharedPreferencesManager(this);
        balance = prefMgr.loadBalance();

        // Бары - батарея и тд для сохранения отступов
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public static long getBalance(){ return balance; }
}