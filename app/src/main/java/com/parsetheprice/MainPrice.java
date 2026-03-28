package com.parsetheprice;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parsetheprice.data.entity.PriceTask;
import com.parsetheprice.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class MainPrice extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PriceItemAdapter adapter;
    private List<PriceTask> priceItems;
    private ImageView addButtonPrice;
    private ImageView balanceButton;
    private SharedPreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_price);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up);
            }
        });
        initViews();
        //initData();  в будущем для работы с балансом
        setupRecyclerView();
        setupFabButtons();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.tasksRecyclerView);
        addButtonPrice = findViewById(R.id.addButtonPrice);
        balanceButton = findViewById(R.id.balanceButton);
    }

//    private void initData() {
//        preferencesManager = new SharedPreferencesManager(this);
//        priceItems = new ArrayList<>();
//    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PriceItemAdapter(priceItems, this, userBalance);
        recyclerView.setAdapter(adapter);
    }

    private void setupFabButtons() {
        addButtonPrice.setOnClickListener(v -> {
        });
        balanceButton.setOnClickListener(v -> {
        });
    }

    public void goBack(View view) {
        finish();
    }
}