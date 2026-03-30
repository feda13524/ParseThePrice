package com.parsetheprice.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parsetheprice.R;
import com.parsetheprice.data.entity.ParseTask;
import com.parsetheprice.ui.adapter.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<ParseTask> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        recyclerView = findViewById(R.id.tasksRecyclerView);
        ImageView addButton = findViewById(R.id.addButtonParse);
        addButton.setOnClickListener(v -> {
            Toast.makeText(this, "Добавление задачи", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        // Подписка на данные
        viewModel.getAllParseTasks().observe(this, tasks -> {
            if (tasks != null) {
                taskList = tasks;
                adapter.setTasks(taskList);
            }
        });

        // Кнопка добавления (пока просто Toast)
        addButton.setOnClickListener(v -> {
            Toast.makeText(this, "Добавление задачи", Toast.LENGTH_SHORT).show();
        });
    }

    public void goBack(View view) {
        finish();
    }
}