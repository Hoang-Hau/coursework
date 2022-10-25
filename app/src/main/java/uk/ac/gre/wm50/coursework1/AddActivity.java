package uk.ac.gre.wm50.coursework1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.time.LocalDate;

public class AddActivity extends AppCompatActivity {

    EditText name_input, destination_input, date_input, description_input;
    Button add_button;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                        RadioButton radioButton = (RadioButton) findViewById(checkId);

                    }
                }
        );

        name_input = findViewById(R.id.name_input);
        destination_input = findViewById(R.id.destination_input);
        date_input = findViewById(R.id.date_input);
        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate localDate = LocalDate.now();
                int year = localDate.getYear();
                int month = localDate.getMonthValue();
                int day = localDate.getDayOfMonth();

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date_input.setText(day + "-" + (month++) + "-" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        description_input = findViewById(R.id.description_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);



                if (name_input == null) {
                    Toast.makeText(AddActivity.this, "You need to fill all require fields", Toast.LENGTH_SHORT).show();
                } else if (destination_input == null) {
                    Toast.makeText(AddActivity.this, "You need to fill all require fields", Toast.LENGTH_SHORT).show();
                } else if (date_input == null) {
                    Toast.makeText(AddActivity.this, "You need to fill all require fields", Toast.LENGTH_SHORT).show();
                } else if ((radioGroup.getCheckedRadioButtonId() == -1)) {
                    Toast.makeText(AddActivity.this, "You need to fill all require fields", Toast.LENGTH_SHORT).show();
                } else if (description_input == null) {
                    Toast.makeText(AddActivity.this, "You need to fill all require fields", Toast.LENGTH_SHORT).show();
                } else {
                    myDB.addProduct(name_input.getText().toString().trim(),
                            destination_input.getText().toString().trim(),
                            date_input.getText().toString().trim(),
                            ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString(),
                            description_input.getText().toString().trim());
                    displayNextAlert(name_input.getText().toString().trim(),
                            destination_input.getText().toString().trim(),
                            date_input.getText().toString().trim(),
                            ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString(),
                            description_input.getText().toString().trim());
                }


            }
        });

    }

    private void displayNextAlert(String strName, String strDestination, String strDate, String strRisk, String strDescription){
        new AlertDialog.Builder(this).setTitle("Details entered").setMessage("Details entered: " +
                "\n   Name:  " + strName +
                "\n   Destination:  " + strDestination +
                "\n   Date of the Trip:  " + strDate +
                "\n   Risk Assessment:  " + strRisk +
                "\n   Description:  " + strDescription

        ).setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }

        }).setNegativeButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }

        }).show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


}