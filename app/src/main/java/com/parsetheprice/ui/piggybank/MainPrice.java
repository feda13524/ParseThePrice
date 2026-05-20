package com.parsetheprice.ui.piggybank;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parsetheprice.R;
import com.parsetheprice.ui.adapter.PriceTaskAdapter;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainPrice extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PriceTaskAdapter adapter;
    private PriceViewModel viewModel;
    private TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_price);
        ImageButton btnBack = findViewById(R.id.btnBack);
        TextView sortButton = findViewById(R.id.sortButton);
        ImageView addButtonPrice = findViewById(R.id.addButtonPrice);
        ImageView balanceButton = findViewById(R.id.balanceButton);
        balance = findViewById(R.id.balanceText);

        recyclerView = findViewById(R.id.tasksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PriceTaskAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(PriceViewModel.class);
        viewModel.getTasks().observe(this, tasks -> {
            if (tasks != null) {
                adapter.setTasks(tasks);
            }
        });

        updateBalanceDisplay();

        adapter.setOnDeleteClickListener((task, position) -> {
            viewModel.delete(task.getId());
        });
        adapter.setOnRefreshClickListener((task, position) -> {
            viewModel.update(task.getId());
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up);
            }
        });

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.changeSortKey();
                switch (viewModel.getSortKey()){
                    case 1:
                        sortButton.setText(getString(R.string.sort_by_expensive));
                        break;
                    case 2:
                        sortButton.setText(getString(R.string.sort_by_cheap));
                        break;
                    default:
                        sortButton.setText(getString(R.string.sort_by_time));
                        break;
                }
                viewModel.getTasks().observe(MainPrice.this, tasks -> {
                    if (tasks != null) {
                        adapter.setTasks(tasks);
                    }
                });
            }
        });

        addButtonPrice.setOnClickListener(v -> {
            AddDialogPrice dialog = new AddDialogPrice();
            dialog.setOnTaskAddedListener((name, link) -> {
                viewModel.insert(name, link);
            });
            dialog.show(getSupportFragmentManager(), "AddTaskDialog");
        });

        balanceButton.setOnClickListener(v -> {
            AddDialogBalance dialog = new AddDialogBalance();
            dialog.setOnBalanceChangeListener(amount -> {
                viewModel.addBalance(amount);
                adapter.updateBalance(viewModel.getBalance());
                updateBalanceDisplay();
            });
            dialog.show(getSupportFragmentManager(), "AddBalanceDialog");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBalanceDisplay();
        if (adapter != null) {
            adapter.updateBalance(viewModel.getBalance());
        }
    }

    private void updateBalanceDisplay() {
        long amount = viewModel.getBalance();
        balance.setText(String.valueOf(amount) + " ₽");
    }
}