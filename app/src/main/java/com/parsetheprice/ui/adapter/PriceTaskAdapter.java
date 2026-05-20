package com.parsetheprice.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
    private long userBalance = 0;
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
    public void updateBalance(long newBalance) {
        this.userBalance = newBalance;
        notifyDataSetChanged();
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
        holder.priceTextView.setText(String.valueOf((int) task.getPrice()) + " ₽");
        int progress = 0;
        double price = task.getPrice();
        if (price > 0) {
            progress = (int) ((userBalance * 100) / price);
            if (progress > 100) {
                progress = 100;
            }
        }
        holder.progressBar.setProgress(progress);

        holder.linkTextView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(task.getLink()));
            holder.itemView.getContext().startActivity(intent);
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
        char status = task.getStatus();

        switch (status) {
            case '+':
                holder.statusTextView.setTextColor(0xFF00FF00);
                holder.statusTextView.setText(holder.context.getString(R.string.ok_status));
                break;
            case '-':
                holder.statusTextView.setTextColor(0xFFFF0000);
                holder.statusTextView.setText(holder.context.getString(R.string.error_status));
                break;
            default:
                holder.statusTextView.setTextColor(0xFFFFA500);
                holder.statusTextView.setText(holder.context.getString(R.string.pending_status));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<PriceTask> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView linkTextView, nameTextView;
        TextView lastUpdatedTextView, priceTextView, statusTextView;
        ImageButton refreshButton, deleteButton;
        ProgressBar progressBar;
        TextView balanceText;
        Context context;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            linkTextView = itemView.findViewById(R.id.linkTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            lastUpdatedTextView = itemView.findViewById(R.id.lastUpdatedTextView);
            refreshButton = itemView.findViewById(R.id.refreshButton);
            priceTextView = itemView.findViewById(R.id.price);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            balanceText = itemView.findViewById(R.id.balanceText);
            progressBar = itemView.findViewById(R.id.progressBar);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }
}