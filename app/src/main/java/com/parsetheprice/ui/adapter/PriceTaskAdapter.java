package com.parsetheprice.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parsetheprice.R;
import com.parsetheprice.data.entity.PriceTask;

import java.util.ArrayList;
import java.util.List;

public class PriceTaskAdapter extends RecyclerView.Adapter<PriceTaskAdapter.ViewHolder> {

    private List<PriceTask> tasks = new ArrayList<>();
    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener;
    private OnRefreshClickListener refreshListener;

    public interface OnItemClickListener {
        void onExpandClick(PriceTask task, int position);
    }
    public interface OnDeleteClickListener {
        void onDeleteClick(PriceTask task, int position);
    }
    public interface OnRefreshClickListener {
        void onRefreshClick(PriceTask task, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void setOnDeleteClickListener(OnDeleteClickListener deleteListener) {
        this.deleteListener = deleteListener;
    }
    public void setOnRefreshClickListener(OnRefreshClickListener refreshListener) {
        this.refreshListener = refreshListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task_price, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PriceTask task = tasks.get(position);
        String link = task.getLink();
        if (link.length() > 40) {
            link = link.substring(0, 37) + "...";
        }
        holder.linkTextView.setText(link);
        holder.nameTextView.setText(task.getName());
        holder.lastUpdatedTextView.setText(task.getFormattedDate());
        //holder.nameTextView.setText(task.getName());

        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDeleteClick(task, position);
            }
        });
        holder.refreshButton.setOnClickListener(v -> {
            if (refreshListener != null) {
                refreshListener.onRefreshClick(task, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<PriceTask> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void addTask(PriceTask task) {
        tasks.add(task);
        notifyItemInserted(tasks.size() - 1);
    }
    public void updateTask(int position) {
        notifyItemChanged(position);
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView linkTextView, nameTextView;
        TextView lastUpdatedTextView;
        ImageButton refreshButton, deleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            linkTextView = itemView.findViewById(R.id.linkTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            lastUpdatedTextView = itemView.findViewById(R.id.lastUpdatedTextView);
            refreshButton = itemView.findViewById(R.id.refreshButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}