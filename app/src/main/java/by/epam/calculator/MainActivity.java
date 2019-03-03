package by.epam.calculator;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.EnumMap;
import by.epam.calculator.calc.CalcOperations;
import by.epam.calculator.enums.ActionType;
import by.epam.calculator.enums.OperationType;
import by.epam.calculator.enums.Symbol;
import by.epam.calculator.exceptions.DivisionByZeroException;

public class MainActivity extends AppCompatActivity {

    private EditText txtResult;
    private EnumMap<Symbol, Object> commands = new EnumMap<>(Symbol.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtResult = findViewById(R.id.txtResult);

        Button add = findViewById(R.id.add);
        Button divide = findViewById(R.id.divide);
        Button multiply = findViewById(R.id.multiply);
        Button subtract = findViewById(R.id.subtract);

        add.setTag(OperationType.ADD);
        divide.setTag(OperationType.DIVIDE);
        multiply.setTag(OperationType.MULTIPLY);
        subtract.setTag(OperationType.SUBTRACT);

    }

    private void showToastMessage() {
        Toast toastMessage = Toast.makeText(this, R.string.division_zero, Toast.LENGTH_LONG);
        toastMessage.setGravity(Gravity.TOP, 0, 100);
        toastMessage.show();
    }

    public void operationMultiply(View view){
        OperationType operType;
        operType = (OperationType) view.getTag();

        if (lastAction == ActionType.OPERAION) {
            commands.put(Symbol.OPERATION, operType);

            return;
        }
        if (!commands.containsKey(Symbol.OPERATION)) {

            if (!commands.containsKey(Symbol.FIRST_DIGIT)) {
                commands.put(Symbol.FIRST_DIGIT, txtResult.getText());
            }
            commands.put(Symbol.OPERATION, operType);
        } else if (!commands.containsKey(Symbol.SECOND_DIGIT)) {
            commands.put(Symbol.SECOND_DIGIT, txtResult.getText());
            doCalc();
            commands.put(Symbol.OPERATION, operType);
            commands.remove(Symbol.SECOND_DIGIT);
        }
        lastAction = ActionType.OPERAION;
    }

    public void operationClear(View view){
        txtResult.setText("0");
        commands.clear();
        lastAction = ActionType.CLEAR;

    }



    public void operationComma(View view){
        if (commands.containsKey(Symbol.FIRST_DIGIT)
                && getDouble(txtResult.getText().toString()) == getDouble(commands.get(Symbol.FIRST_DIGIT).toString()))
        {
            txtResult.setText("0" + view.getContentDescription().toString());
        }

        if (!txtResult.getText().toString().contains(",")) {
            txtResult.setText(txtResult.getText() + ",");
        }

        lastAction = ActionType.COMMA;
    }
    public void operationDelete(View view){
        txtResult.setText(txtResult.getText().delete(
                txtResult.getText().length() - 1,
                txtResult.getText().length()));

        if (txtResult.getText().toString().trim().length() == 0) {
            txtResult.setText("0");
        }

        lastAction = ActionType.DELETE;
    }
    public void operationResult(View view){
        if (lastAction == ActionType.CALCULATION)
        {
            return;
        }

        if (commands.containsKey(Symbol.FIRST_DIGIT)
                && commands.containsKey(Symbol.OPERATION)) {
            commands.put(Symbol.SECOND_DIGIT, txtResult.getText());
            doCalc();
            commands.clear();
        }
        lastAction = ActionType.CALCULATION;
    }


    private ActionType lastAction;

    public void buttonClick(View view) {

        switch (view.getId()) {
            case R.id.add:
            case R.id.subtract:
            case R.id.divide:
            case R.id.multiply: {
                operationMultiply(findViewById(R.id.multiply));

                break;
            }
            case R.id.clear: {
                operationClear(findViewById(R.id.clear));

                break;
            }
            case R.id.result: {
                operationResult(findViewById(R.id.result));

                break;
            }
            case R.id.comma: {
                operationComma(findViewById(R.id.comma));

                break;
            }
            case R.id.delete: {
                operationDelete(findViewById(R.id.delete));

                break;
            }

            default: {
                if (txtResult.getText().toString().equals("0")
                        ||(commands.containsKey(Symbol.FIRST_DIGIT) && getDouble(txtResult.getText()) == getDouble(commands.get(Symbol.FIRST_DIGIT)))
                        || (lastAction == ActionType.CALCULATION)
                ) {

                    txtResult.setText(view.getContentDescription().toString());
                } else {
                    txtResult.setText(txtResult.getText()+ view.getContentDescription().toString());
                }
                lastAction = ActionType.DIGIT;
            }

        }
    }
    private double getDouble(Object value) {
        double result = 0;
        try {
            result = Double.valueOf(value.toString().replace(',', '.'));
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }

        return result;
    }

    private void doCalc() {
        OperationType operTypeTmp = (OperationType) commands.get(Symbol.OPERATION);
        double result = 0;

        try {
            result = calc(operTypeTmp,getDouble(commands.get(Symbol.FIRST_DIGIT)),getDouble(commands.get(Symbol.SECOND_DIGIT)));
        } catch (DivisionByZeroException e) {
            showToastMessage();
            return;
        }

        if (result % 1 == 0) {
            txtResult.setText(String.valueOf((int) result));
        } else {
            txtResult.setText(String.valueOf(result));
        }

        commands.put(Symbol.FIRST_DIGIT, result);
    }



    private Double calc(OperationType operType, double a, double b) {
        switch (operType) {
            case ADD: {
                return CalcOperations.add(a, b);
            }
            case DIVIDE: {
                return CalcOperations.divide(a, b);
            }
            case MULTIPLY: {
                return CalcOperations.multiply(a, b);
            }
            case SUBTRACT: {
                return CalcOperations.subtract(a, b);
            }
        }

        return null;
    }


}
