package uk.ac.gre.wm50.coursework1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.time.LocalDate;

public class UpdateActivity extends AppCompatActivity {

    EditText name_input, destination_input, date_input, description_input;
    Button update_button, expense_button;
    private RadioGroup radioGroup;


    String id, name, destination, date, risk, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name_input = findViewById(R.id.name_input2);
        destination_input = findViewById(R.id.destination_input2);
        date_input = findViewById(R.id.date_input2);
        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate localDate = LocalDate.now();
                int year = localDate.getYear();
                int month = localDate.getMonthValue();
                int day = localDate.getDayOfMonth();

                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date_input.setText(day + "-" + (month++) + "-" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);
        description_input = findViewById(R.id.description_input2);
        update_button = findViewById(R.id.update_button);
        expense_button = findViewById(R.id.expense_button);

        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);


                if (name_input == null) {
                    Toast.makeText(UpdateActivity.this, "You need to fill all require fields", Toast.LENGTH_SHORT).show();
                } else if (destination_input == null) {
                    Toast.makeText(UpdateActivity.this, "You need to fill all require fields", Toast.LENGTH_SHORT).show();
                } else if (date_input == null) {
                    Toast.makeText(UpdateActivity.this, "You need to fill all require fields", Toast.LENGTH_SHORT).show();
                } else if ((radioGroup.getCheckedRadioButtonId() == -1)) {
                    Toast.makeText(UpdateActivity.this, "You need to fill all require fields", Toast.LENGTH_SHORT).show();
                } else if (description_input == null) {
                    Toast.makeText(UpdateActivity.this, "You need to fill all require fields", Toast.LENGTH_SHORT).show();
                } else {
                    myDB.updateData(id, name_input.getText().toString().trim(),
                            destination_input.getText().toString().trim(),
                            date_input.getText().toString().trim(),
                            ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString(),
                            description_input.getText().toString().trim());

                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });

        expense_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripId = getIntent().getStringExtra("id");
                Intent intent = new Intent(UpdateActivity.this, ExpenseActivity.class);
                intent.putExtra("tripID",tripId);
                startActivity(intent);
            }
        });

    }
    void getAndSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("destination") && getIntent().hasExtra("date") &&
                getIntent().hasExtra("risk") && getIntent().hasExtra("description")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            destination = getIntent().getStringExtra("destination");
            date = getIntent().getStringExtra("date");
            risk = getIntent().getStringExtra("risk");
            description = getIntent().getStringExtra("description");

            //Setting Intent Data
            name_input.setText(name);
            destination_input.setText(destination);
            date_input.setText(date);
            radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);
            description_input.setText(description);

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}