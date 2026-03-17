package com.parsetheprice.parse;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parsetheprice.R;
import com.parsetheprice.data.entity.ParseTask;
import com.parsetheprice.parse.Parse_task_adapter;
import com.parsetheprice.parse.add_dialog;
import java.util.ArrayList;
import java.util.List;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.parsetheprice.R;

public class MainParse extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Parse_task_adapter adapter;
    private List<ParseTask> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_parse);
        ImageButton btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
            }
        });

        recyclerView = findViewById(R.id.tasksRecyclerView);
        ImageView addButton = findViewById(R.id.addButtonParse);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Parse_task_adapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((task, position) -> {
            task.setExpanded(!task.isExpanded());
            adapter.notifyItemChanged(position);
        });

        adapter.setOnDeleteClickListener((task, position) -> {
            taskList.remove(position);
            adapter.setTasks(taskList);
        });
        adapter.setOnRefreshClickListener((task, position) -> {
            task.updateTimestamp();  // обновляем время
            adapter.updateTask(position);  // обновляем отображение
        });

        addButton.setOnClickListener(v -> {
            add_dialog dialog = new add_dialog();
            dialog.setOnTaskAddedListener(task -> {
                taskList.add(task);
                adapter.setTasks(taskList);
            });
            dialog.show(getSupportFragmentManager(), "AddTaskDialog");
        });
    }

    public void goBack(View view) {
        finish();
    }
}