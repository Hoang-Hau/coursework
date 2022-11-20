package uk.ac.gre.wm50.coursework1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.time.LocalDate;

public class AddExpenseActivity extends AppCompatActivity {

    Spinner spinner;
    EditText amount_input, expense_date_input;
    Button expense_add_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        amount_input = findViewById(R.id.amount_input);
        expense_date_input = findViewById(R.id.expense_date_input);
        expense_date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate localDate = LocalDate.now();
                int year = localDate.getYear();
                int month = localDate.getMonthValue();
                int day = localDate.getDayOfMonth();

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddExpenseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                expense_date_input.setText(day + "-" + (month++) + "-" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("tripID"));
        expense_add_button = findViewById(R.id.expense_add_button);
        expense_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseExpense myDB = new MyDatabaseExpense(AddExpenseActivity.this);

                if (amount_input.length()==0 ) {
                    Toast.makeText(AddExpenseActivity.this, "You need to fill all require fields", Toast.LENGTH_SHORT).show();
                } else if ((expense_date_input.length()==0)) {
                    Toast.makeText(AddExpenseActivity.this, "You need to fill all require fields", Toast.LENGTH_SHORT).show();
                } else {
                    myDB.addExpense(spinner.getSelectedItem().toString(),
                            amount_input.getText().toString().trim(),
                            expense_date_input.getText().toString().trim(), id);

                            Intent intent = new Intent(AddExpenseActivity.this, ExpenseActivity.class);
                            intent.putExtra("tripID", String.valueOf(id));
                            startActivity(intent);
                }

            }
        });
    }

}

