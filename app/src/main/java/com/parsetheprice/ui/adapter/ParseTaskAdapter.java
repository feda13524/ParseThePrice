package com.parsetheprice.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.parsetheprice.R;
import com.parsetheprice.data.entity.ParseTask;
import java.util.ArrayList;
import java.util.List;

public class ParseTaskAdapter extends RecyclerView.Adapter<ParseTaskAdapter.ViewHolder> {

    private List<ParseTask> tasks = new ArrayList<>();
    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener;
    private OnRefreshClickListener refreshListener;

    public interface OnItemClickListener {
        void onExpandClick(ParseTask task, int position);
    }
    public interface OnDeleteClickListener {
        void onDeleteClick(ParseTask task, int position);
    }
    public interface OnRefreshClickListener {
        void onRefreshClick(ParseTask task, int position);
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
                .inflate(R.layout.item_task_parse, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ParseTask task = tasks.get(position);
        String link = task.getLink();
        if (link.length() > 40) {
            link = link.substring(0, 37) + "...";
        }
        holder.linkTextView.setText(link);
        holder.nameTextView.setText(task.getName());
        holder.lastUpdatedTextView.setText(task.getFormattedDate());
        if (task.getIsExpanded()) {
            holder.expandedContent.setVisibility(View.VISIBLE);
            holder.expandButton.setRotation(180);
        } else {
            holder.expandedContent.setVisibility(View.GONE);
            holder.expandButton.setRotation(0);
        }
        //holder.nameTextView.setText(task.getName());
        holder.userTextTextView.setText(task.getMessage());

        holder.expandButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onExpandClick(task, position);
            }
        });
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

    public void setTasks(List<ParseTask> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void addTask(ParseTask task) {
        tasks.add(task);
        notifyItemInserted(tasks.size() - 1);
    }
    public void updateTask(int position) {
        notifyItemChanged(position);
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView linkTextView, userTextTextView, nameTextView;
        TextView lastUpdatedTextView;
        ImageButton refreshButton, deleteButton;
        ImageView expandButton;
        LinearLayout expandedContent;

        ViewHolder(View itemView) {
            super(itemView);
            linkTextView = itemView.findViewById(R.id.linkTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            userTextTextView = itemView.findViewById(R.id.userTextTextView);
            lastUpdatedTextView = itemView.findViewById(R.id.lastUpdatedTextView);
            expandButton = itemView.findViewById(R.id.expandButton);
            refreshButton = itemView.findViewById(R.id.refreshButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            expandedContent = itemView.findViewById(R.id.expandedContent);
        }
    }
}