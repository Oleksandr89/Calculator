package com.example.calculator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private EditText firstNumberEditText, secondNumberEditText;
    private RadioButton plusRadioButton, minusRadioButton, divideRadioButton, multiplyRadioButton;
    private CheckBox floatValues, signedValues;
    private String firstNumber, secondNumber;
    private AlertDialog.Builder builder;
    private RadioGroup firstdRadioGroup, secondRadioGroup;

    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Find View elements");
        firstNumberEditText = findViewById(R.id.firstNumberEditText);
        secondNumberEditText = findViewById(R.id.secondNumberEditText);
        plusRadioButton = findViewById(R.id.plusRadioButton);
        minusRadioButton = findViewById(R.id.minusRadioButton);
        divideRadioButton = findViewById(R.id.divideRadioButton);
        multiplyRadioButton = findViewById(R.id.multiplyRadioButton);
        resultTextView = findViewById(R.id.resultTextView);
        floatValues = findViewById(R.id.floatValues);
        signedValues = findViewById(R.id.signedValues);
        firstdRadioGroup = findViewById(R.id.firstRadioGroup);
        secondRadioGroup = findViewById(R.id.secondRadioGroup);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "Create Menu");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "Click Menu Item");
        switch (item.getItemId()) {
            case R.id.actionClear:
                showAlertClearDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAlertClearDialog() {
        Log.d(TAG, "Show alert dialog Clear");
        builder = new AlertDialog.Builder(this);
        builder
                .setTitle(getString(R.string.alertTitle))
                .setMessage(getString(R.string.alertMessage))
                .setPositiveButton(getString(R.string.alertButtonOk), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "Click button Ok");
                        onClickClear();
                        Toast.makeText(MainActivity.this, getString(R.string.toastClickButtonOk), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getString(R.string.alertButtonCancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "Click button Cancel");
                        Toast.makeText(MainActivity.this, getString(R.string.toastClickButtonCancel), Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
        builder.show();
    }

    public void calculateButtonClick(View view) {
        Log.d(TAG, "Click button calculate");
        firstNumber = firstNumberEditText.getText().toString();
        secondNumber = secondNumberEditText.getText().toString();

        if (TextUtils.isEmpty(firstNumber)) {
            Log.d(TAG, "Check first line is empty");
            firstNumberEditText.setError(getString(R.string.errorMessageEmptyFirstLine));
        } else if (TextUtils.isEmpty(secondNumber)) {
            Log.d(TAG, "Check second line is empty");
            secondNumberEditText.setError(getString(R.string.errorMessageEmptySecondLine));
        } else {
            float firstNum = Float.parseFloat(firstNumber);
            float secondNum = Float.parseFloat(secondNumber);
            Float result;

            Log.d(TAG, "Calculate numbers");
            if (plusRadioButton.isChecked()) {
                result = firstNum + secondNum;
            } else if (minusRadioButton.isChecked()) {
                result = firstNum - secondNum;
            } else if (divideRadioButton.isChecked()) {
                if (secondNum == 0 || secondNum == 0.0) {
                    Toast.makeText(this, getString(R.string.divisionByZero), Toast.LENGTH_SHORT).show();
                    return;
                }
                result = firstNum / secondNum;
            } else if (multiplyRadioButton.isChecked()) {
                result = firstNum * secondNum;
            } else {
                result = 0f;
            }
            Log.d(TAG, "Show result");
            if (floatValues.isChecked()) {
                resultTextView.setText(result.toString());

            } else {
                resultTextView.setText(((Integer) result.intValue()).toString());
            }

        }
    }

    public void onClickClear() {
        Log.d(TAG, "All number Clear");
        firstNumberEditText.setText("");
        secondNumberEditText.setText("");
        resultTextView.setText("");
    }

    public void onClickValue(View view) {
        Integer flag = InputType.TYPE_CLASS_NUMBER;

        if (floatValues.isChecked()) {
            Log.d(TAG, "Check radio button float");
            flag = flag | InputType.TYPE_NUMBER_FLAG_DECIMAL;
        }
        if (signedValues.isChecked()) {
            Log.d(TAG, "Check radio button signed");
            flag = flag | InputType.TYPE_NUMBER_FLAG_SIGNED;
        }

        firstNumberEditText.setInputType(flag);
        secondNumberEditText.setInputType(flag);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plusRadioButton:
            case R.id.minusRadioButton:
            case R.id.divideRadioButton:
            case R.id.multiplyRadioButton:
                firstdRadioGroup.clearCheck();
                secondRadioGroup.clearCheck();
                ((RadioButton) view).setChecked(true);
                break;

        }

    }
}
