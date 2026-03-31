package com.parsetheprice.ui.piggybank;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.parsetheprice.R;

public class AddDialogBalance extends DialogFragment {
    private TextView InputNumber;
    private ImageView btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private ImageView btnDelete;
    private Button btnAdd;
    private Button btnSubtract;

    private StringBuilder inputNumber = new StringBuilder();
    private OnBalanceChangeListener listener;

    public interface OnBalanceChangeListener {
        void onBalanceChanged(long newBalance);
    }

    public void setOnBalanceChangeListener(OnBalanceChangeListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_dialog_balance, container, false);

        initViews(view);
        setupNumberButtons();
        setupDeleteButton();
        setupAddButton();
        setupSubtractButton();
        updateInputDisplay();

        return view;
    }

    private void initViews(View view) {
        InputNumber = view.findViewById(R.id.InputNumber);

        btn0 = view.findViewById(R.id.btn0);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);
        btn5 = view.findViewById(R.id.btn5);
        btn6 = view.findViewById(R.id.btn6);
        btn7 = view.findViewById(R.id.btn7);
        btn8 = view.findViewById(R.id.btn8);
        btn9 = view.findViewById(R.id.btn9);

        btnDelete = view.findViewById(R.id.btnDelete);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnSubtract = view.findViewById(R.id.btnSubtract);
    }

    private void setupNumberButtons() {
        // Для цифры 1
        btn1.setOnClickListener(v -> {
            inputNumber.append("1");
            updateInputDisplay();
        });

        btn2.setOnClickListener(v -> {
            inputNumber.append("2");
            updateInputDisplay();
        });

        btn3.setOnClickListener(v -> {
            inputNumber.append("3");
            updateInputDisplay();
        });

        btn4.setOnClickListener(v -> {
            inputNumber.append("4");
            updateInputDisplay();
        });

        btn5.setOnClickListener(v -> {
            inputNumber.append("5");
            updateInputDisplay();
        });

        btn6.setOnClickListener(v -> {
            inputNumber.append("6");
            updateInputDisplay();
        });

        btn7.setOnClickListener(v -> {
            inputNumber.append("7");
            updateInputDisplay();
        });

        btn8.setOnClickListener(v -> {
            inputNumber.append("8");
            updateInputDisplay();
        });

        btn9.setOnClickListener(v -> {
            inputNumber.append("9");
            updateInputDisplay();
        });

        btn0.setOnClickListener(v -> {
            inputNumber.append("0");
            updateInputDisplay();
        });
    }
    private void setupDeleteButton() {
        btnDelete.setOnClickListener(v -> {
            if (inputNumber.length() > 0) {
                inputNumber.deleteCharAt(inputNumber.length() - 1);
                updateInputDisplay();
            }
        });
    }
    private void setupAddButton() {
        btnAdd.setOnClickListener(v -> {
            long amount = getInputValue();
            if (amount > 0) {
                clearInput();
                if (listener != null) {
                    listener.onBalanceChanged(amount);
                }
            }
        });
    }
    private void setupSubtractButton() {
        btnSubtract.setOnClickListener(v -> {
            long amount = getInputValue();
            if (amount > 0) {
                clearInput();
                if (listener != null) {
                    listener.onBalanceChanged(-amount);
                }
            }
        });
    }
    private long getInputValue() {
        if (TextUtils.isEmpty(inputNumber.toString())) {
            return 0;
        }
        return Long.parseLong(inputNumber.toString());
    }
    private void updateInputDisplay() {
        if (inputNumber.length() == 0) {
            InputNumber.setText("0");
        } else {
            InputNumber.setText(inputNumber.toString());
        }
    }
    private void clearInput() {
        inputNumber.setLength(0);
        updateInputDisplay();
    }
}