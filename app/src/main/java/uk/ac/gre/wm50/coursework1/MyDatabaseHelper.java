package uk.ac.gre.wm50.coursework1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

class MyDatabaseHelper extends SQLiteOpenHelper {


    private Context context;
    private static final String DATABASE_NAME = "Trip.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_shop";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "trip_name";
    private static final String COLUMN_DESTINATION = "_destination";
    private static final String COLUMN_DATE = "_date";
    private static final String COLUMN_RISK = "_risk";
    private static final String COLUMN_DESCRIPTION = "_description";





    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_DESTINATION + " TEXT, " +
                        COLUMN_DATE + " TEXT, " +
                        COLUMN_RISK + " TEXT, " +
                        COLUMN_DESCRIPTION + " TEXT);";
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);


    }



    void addProduct(String name, String destination, String date, String risk, String description) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESTINATION, destination);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_RISK, risk);
        cv.put(COLUMN_DESCRIPTION, description);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!!!", Toast.LENGTH_SHORT).show();
        }
    }



    Cursor readAllData() {
        String query = " SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null );

        }
        return cursor;
    }


    void updateData(String row_id, String name, String destination, String date, String risk, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESTINATION, destination);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_RISK, risk);
        cv.put(COLUMN_DESCRIPTION, description);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Update Successfully!!!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }


    public void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted Successfully!!!", Toast.LENGTH_SHORT).show();
        }
    }

    ArrayList<SearchModel> findAll(){
        ArrayList<SearchModel> searchs = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        searchs = new ArrayList<SearchModel>();
        while(cursor.moveToNext()){
            SearchModel search = new SearchModel();

            search.setSearchId(cursor.getString(0));
            search.setSearchName(cursor.getString(1));
            search.setSearchDestination(cursor.getString(2));
            search.setSearchDate(cursor.getString(3));
            search.setSearchRisk(cursor.getString(4));
            search.setSearchDescription(cursor.getString(5));
            searchs.add(search);

            }

        return  searchs;
    }
}

