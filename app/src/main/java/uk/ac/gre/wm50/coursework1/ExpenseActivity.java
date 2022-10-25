package uk.ac.gre.wm50.coursework1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;



    MyDatabaseExpense myexpenseDB;
    ArrayList<String> expense_id, expense_type, expense_amount, expense_date, trip_id;
    ExpenseAdapter expenseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        int id = Integer.parseInt(getIntent().getStringExtra("tripID"));
        recyclerView = findViewById(R.id.recyclerView);
        empty_imageview = findViewById(R.id.empty_imageview);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseActivity.this, AddExpenseActivity.class);
                intent.putExtra("tripID", String.valueOf(id));
                startActivity(intent);
            }
        });

        myexpenseDB = new MyDatabaseExpense(ExpenseActivity.this);
        expense_id = new ArrayList<>();
        expense_type = new ArrayList<>();
        expense_amount = new ArrayList<>();
        expense_date= new ArrayList<>();
        trip_id = new ArrayList<>();

        displayData(id);

        expenseAdapter = new ExpenseAdapter(ExpenseActivity.this, expense_id, expense_type, expense_amount, expense_date, trip_id);
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExpenseActivity.this));
    }

    void displayData(int id) {
        Cursor cursor = myexpenseDB.readAllData(id);
        if(cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                expense_id.add(cursor.getString(0));
                expense_type.add(cursor.getString(1));
                expense_amount.add(cursor.getString(2));
                expense_date.add(cursor.getString(3));

            }
            empty_imageview.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();

        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseExpense myexpenseDB = new MyDatabaseExpense(ExpenseActivity.this);
                myexpenseDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(ExpenseActivity.this, ExpenseActivity.class);
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