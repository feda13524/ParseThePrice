package com.parsetheprice.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parsetheprice.R;
import com.parsetheprice.data.entity.ParseTask;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<ParseTask> tasks = new ArrayList<>();

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_parse_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        ParseTask task = tasks.get(position);
        holder.nameTextView.setText(task.getName());

        String link = task.getLink();
        if (link.length() > 40) {
            link = link.substring(0, 37) + "...";
        }
        holder.linkTextView.setText(link);
        holder.lastUpdatedTextView.setText(task.getFormattedDate());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<ParseTask> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView linkTextView;
        TextView lastUpdatedTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            linkTextView = itemView.findViewById(R.id.linkTextView);
            lastUpdatedTextView = itemView.findViewById(R.id.lastUpdatedTextView);
        }
    }
}