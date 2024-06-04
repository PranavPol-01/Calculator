package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private String currentNumber = "";
    private String operator = "";
    private double firstNumber = 0.0;
    private boolean isOperatorClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        setNumberButtonListeners();
        setOperatorButtonListeners();
        setControlButtonListeners();
    }

    private void setNumberButtonListeners() {
        int[] numberButtons = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDecimal};

        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (button.getId() == R.id.btnDecimal) {
                    if (!currentNumber.contains(".")) {
                        currentNumber += button.getText().toString();
                    }
                } else {
                    currentNumber += button.getText().toString();
                }
                tvResult.setText(currentNumber);
            }
        };

        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(numberListener);
        }
    }

    private void setOperatorButtonListeners() {
        int[] operatorButtons = {R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide};

        View.OnClickListener operatorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentNumber.isEmpty()) {
                    firstNumber = Double.parseDouble(currentNumber);
                    currentNumber = "";
                    Button button = (Button) v;
                    operator = button.getText().toString();
                    isOperatorClicked = true;
                }
            }
        };

        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(operatorListener);
        }
    }

    private void setControlButtonListeners() {
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCalculator();
            }
        });

        findViewById(R.id.btnEquals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOperatorClicked && !currentNumber.isEmpty()) {
                    double secondNumber = Double.parseDouble(currentNumber);
                    tvResult.setText(firstNumber + operator + secondNumber);
                    double result = performCalculation(secondNumber);
                    tvResult.setText(String.valueOf(result));
                    resetCalculatorAfterResult(result);
                }
            }
        });
    }

    private double performCalculation(double secondNumber) {
        switch (operator) {
            case "+":
                return firstNumber + secondNumber;
            case "-":
                return firstNumber - secondNumber;
            case "*":
                return firstNumber * secondNumber;
            case "/":
                if (secondNumber != 0) {
                    return firstNumber / secondNumber;
                } else {
                    tvResult.setText("Error");
                    return 0;
                }
            default:
                return 0;
        }
    }

    private void resetCalculator() {
        currentNumber = "";
        operator = "";
        firstNumber = 0.0;
        isOperatorClicked = false;
        tvResult.setText("0");
    }

    private void resetCalculatorAfterResult(double result) {
        currentNumber = String.valueOf(result);
        operator = "";
        firstNumber = result;
        isOperatorClicked = false;
    }
}
