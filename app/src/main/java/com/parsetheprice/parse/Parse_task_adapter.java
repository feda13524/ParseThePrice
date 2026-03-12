package com.parsetheprice.parse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.parsetheprice.R;
import com.parsetheprice.data.entity.ParseTask;
import java.util.ArrayList;
import java.util.List;

public class Parse_task_adapter extends RecyclerView.Adapter<Parse_task_adapter.ViewHolder> {

    private List<ParseTask> tasks = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onExpandClick(ParseTask task, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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

        if (task.isExpanded()) {
            holder.expandedContent.setVisibility(View.VISIBLE);
            holder.expandButton.setRotation(180);
        } else {
            holder.expandedContent.setVisibility(View.GONE);
            holder.expandButton.setRotation(0);
        }
        holder.nameTextView.setText(task.getName());
        holder.userTextTextView.setText(task.getMessage());

        holder.expandButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onExpandClick(task, position);
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView linkTextView, userTextTextView, fullLinkTextView, nameTextView;
        ImageView expandButton;
        LinearLayout expandedContent;

        ViewHolder(View itemView) {
            super(itemView);
            linkTextView = itemView.findViewById(R.id.linkTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            userTextTextView = itemView.findViewById(R.id.userTextTextView);
            expandButton = itemView.findViewById(R.id.expandButton);
            expandedContent = itemView.findViewById(R.id.expandedContent);
        }
    }
}