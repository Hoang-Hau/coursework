package uk.ac.gre.wm50.coursework1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseExpense extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Expense.db";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_NAME_EXPENSE = "my_expense";
    private static final String COLUMN_EXPENSES = "_id";
    private static final String COLUMN_TRIP_ID = "trip_id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DATE_EXPENSE = "date";


    MyDatabaseExpense(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String query_expense =
                "CREATE TABLE " + TABLE_NAME_EXPENSE +
                        " (" + COLUMN_EXPENSES + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TYPE + " TEXT, " +
                        COLUMN_AMOUNT + " TEXT, " +
                        COLUMN_DATE_EXPENSE + " TEXT, " +
                        COLUMN_TRIP_ID+ " INTEGER, " +
                        "FOREIGN KEY ("+ COLUMN_TRIP_ID + ") REFERENCES TABLE_NAME(COLUMN_ID));";


        db.execSQL(query_expense);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EXPENSE);
        onCreate(db);

    }

    void addExpense(String type, String amount, String date, int trip_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_AMOUNT, amount);
        cv.put(COLUMN_DATE_EXPENSE, date);
        cv.put(COLUMN_TRIP_ID, trip_id);
        long result = db.insert(TABLE_NAME_EXPENSE, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!!!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(int tripID) {

        String query_expense = " SELECT _id, type, amount, date  FROM " + TABLE_NAME_EXPENSE + " where trip_id = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query_expense, new String[]{String.valueOf(tripID)});
        }
        return cursor;
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_EXPENSE);
    }
}
