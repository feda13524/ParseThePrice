package com.parsetheprice.ui.parse;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parsetheprice.R;
import com.parsetheprice.data.entity.ParseTask;
import com.parsetheprice.ui.adapter.ParseTaskAdapter;

import java.util.ArrayList;
import java.util.List;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainParse extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ParseTaskAdapter adapter;
    private List<ParseTask> taskList = new ArrayList<>();
    private ParseViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_parse);
        ImageButton btnBack = findViewById(R.id.btnBack);
        viewModel = new ViewModelProvider(this).get(ParseViewModel.class);

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
        adapter = new ParseTaskAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getAllTasks().observe(this, tasks -> {
            if (tasks != null) {
                adapter.setTasks(tasks);
            }
        });

        adapter.setOnItemClickListener((task, position) -> {
            task.setIsExpanded(!task.getIsExpanded());
            adapter.notifyItemChanged(position);
        });

        adapter.setOnDeleteClickListener((task, position) -> {
            viewModel.delete(task.getId());
        });
        adapter.setOnRefreshClickListener((task, position) -> {
            viewModel.update(task.getId());
        });

        addButton.setOnClickListener(v -> {
            AddDialogParse dialog = new AddDialogParse();
            dialog.setOnTaskAddedListener((name, link, massage) -> {
                viewModel.insert(name, link, massage);
            });
            dialog.show(getSupportFragmentManager(), "AddTaskDialog");
        });
    }

    public void goBack(View view) {
        finish();
    }
}