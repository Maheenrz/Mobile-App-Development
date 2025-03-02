package com.example.easycalculator;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttondot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        // Initialize resultTv to be empty
        resultTv.setText("0");

        assignId(buttonC, R.id.button_c);
        assignId(buttonBrackOpen, R.id.button_open_bracket);
        assignId(buttonBrackClose, R.id.button_close_bracket);
        assignId(buttonDivide, R.id.button_divide);
        assignId(buttonMultiply, R.id.button_multiply);
        assignId(buttonPlus, R.id.button_plus);
        assignId(buttonMinus, R.id.button_minus);
        assignId(buttonEquals, R.id.button_equals);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonAC, R.id.button_AC);
        assignId(buttondot, R.id.button_dot);
    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }

        if (buttonText.equals("=")) {
            // Calculate and display the result
            String finalResult = getResult(dataToCalculate);
            if (!finalResult.equals("Err")) {
                resultTv.setText(finalResult);
            } else {
                resultTv.setText("Err"); // Handle evaluation errors
            }
            return;
        }

        if (buttonText.equals("C")) {
            if (!dataToCalculate.isEmpty()) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }

        solutionTv.setText(dataToCalculate);
    }
    String getResult(String data) {
        if (data.isEmpty()) {
            return "Err"; // Return "Err" for empty input
        }

        Context context = null;
        try {
            // Check for invalid expressions
            if (isInvalidExpression(data)) {
                return "Err";
            }

            context = Context.enter();
            context.setOptimizationLevel(-1);

            Scriptable scriptable = context.initSafeStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "javascript", 1, null).toString();

            // Remove trailing ".0" for whole numbers
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }

            return finalResult;
        } catch (Exception e) {
            return "Err"; // Return "Err" for any evaluation errors
        } finally {
            if (context != null) {
                Context.exit(); // Ensure the context is exited
            }
        }
    }

    boolean isInvalidExpression(String data) {
        // Check for empty input
        if (data.isEmpty()) {
            return true;
        }

        // Check for unmatched brackets
        int openBrackets = 0;
        for (char c : data.toCharArray()) {
            if (c == '(') {
                openBrackets++;
            } else if (c == ')') {
                openBrackets--;
            }
        }
        if (openBrackets != 0) {
            return true; // Unmatched brackets
        }

        // Check for invalid operator sequences (e.g., "5 + * 3")
        if (data.matches(".*[+\\-*/]{2,}.*")) {
            return true; // Consecutive operators
        }

        // Check for invalid characters
        if (!data.matches("[0-9+\\-*/() .]+")) {
            return true; // Invalid characters
        }

        return false; // Expression is valid
    }
}