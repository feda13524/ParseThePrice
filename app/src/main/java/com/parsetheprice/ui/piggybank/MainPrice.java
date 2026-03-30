package com.parsetheprice.ui.piggybank;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parsetheprice.R;
import com.parsetheprice.data.entity.PriceTask;
import com.parsetheprice.ui.adapter.PriceTaskAdapter;

import java.util.ArrayList;
import java.util.List;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainPrice extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PriceTaskAdapter adapter;
    private List<PriceTask> priceItems = new ArrayList<>();

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
        recyclerView = findViewById(R.id.tasksRecyclerView);
        ImageView addButtonPrice = findViewById(R.id.addButtonPrice);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PriceTaskAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((task, position) -> {
            task.setIsExpanded(!task.getIsExpanded());
            adapter.notifyItemChanged(position);
        });

        adapter.setOnDeleteClickListener((task, position) -> {
            priceItems.remove(position);
            adapter.setTasks(priceItems);
        });
        adapter.setOnRefreshClickListener((task, position) -> {
            task.updateTime();  // обновляем время
            adapter.updateTask(position);  // обновляем отображение
        });

        addButtonPrice.setOnClickListener(v -> {
            AddDialogPrice dialog = new AddDialogPrice();
            dialog.setOnTaskAddedListener(task -> {
                priceItems.add(task);
                adapter.setTasks(priceItems);
            });
            dialog.show(getSupportFragmentManager(), "AddTaskDialog");
        });
    }
    public void goBack(View view) {
        finish();
    }
}