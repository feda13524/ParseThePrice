package com.parsetheprice.parse;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.parsetheprice.R;
import com.parsetheprice.data.entity.ParseTask;

public class AddDialogParse extends DialogFragment {

    private OnTaskAddedListener listener;

    public interface OnTaskAddedListener {
        void onTaskAdded(ParseTask task);
    }

    public void setOnTaskAddedListener(OnTaskAddedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_dialog_parse, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText nameEditText = view.findViewById(R.id.nameEditText);
        EditText linkEditText = view.findViewById(R.id.linkEditText);
        EditText userTextEditText = view.findViewById(R.id.userTextEditText);
        ImageView addButton = view.findViewById(R.id.addButtonTask);
        ImageButton closeButton = view.findViewById(R.id.closeButton);

        closeButton.setOnClickListener(v -> {
            dismiss();
        });

        addButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String link = linkEditText.getText().toString().trim();
            String userText = userTextEditText.getText().toString().trim();

            if (!name.isEmpty() && !link.isEmpty() && !userText.isEmpty()) {
                ParseTask task = new ParseTask(name, link, userText);
                if (listener != null) {
                    listener.onTaskAdded(task);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}