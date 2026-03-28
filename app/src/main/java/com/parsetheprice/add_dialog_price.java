package com.parsetheprice;

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
import com.parsetheprice.data.entity.PriceTask;

public class add_dialog_price extends DialogFragment {

    private OnTaskAddedListener listener;

    public interface OnTaskAddedListener {
        void onTaskAdded(PriceTask task);
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
        return inflater.inflate(R.layout.add_dialog_price, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText nameEditText = view.findViewById(R.id.nameEditText);
        EditText linkEditText = view.findViewById(R.id.linkEditText);
        ImageView addButton = view.findViewById(R.id.addButtonPrice);
        ImageButton closeButton = view.findViewById(R.id.closeButton);

        closeButton.setOnClickListener(v -> {
            dismiss();
        });

        addButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String link = linkEditText.getText().toString().trim();

            if (!name.isEmpty() && !link.isEmpty()) {
                PriceTask task = new PriceTask(name, link);
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