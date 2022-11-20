package uk.ac.gre.wm50.coursework1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.gre.wm50.coursework1.api.APIClient;
import uk.ac.gre.wm50.coursework1.api.APIInterface;
import uk.ac.gre.wm50.coursework1.api.TripCloud;

public class UploadActivity extends AppCompatActivity {


    TextView textView, responseHead,text;
    MyDatabaseHelper myDB = new MyDatabaseHelper(UploadActivity.this);
    List<String> tripName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        getNameTrip();

        textView = findViewById(R.id.responseHead);

        responseHead = findViewById(R.id.response);
        Button button = findViewById(R.id.send);
        text = findViewById(R.id.textView);
        text.setText(finalJson(setJson(tripName)));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadPost();
            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nevigation);

        bottomNavigationView.setSelectedItemId(R.id.upload);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.upload:
                        return true;


                }
                return false;
            }
        });
    }


    private void UploadPost(){
        String json = finalJson(setJson(tripName));
        APIInterface apiInterface = APIClient.getRetrofitInstance().create(APIInterface.class);
        Call<TripCloud> call = apiInterface.getTripInformation(json);
        call.enqueue(new Callback<TripCloud>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<TripCloud> call, Response<TripCloud> response) {
                if(response.isSuccessful()){
                    textView.setText("Localhost Connect onResponse:" + response.code());
                    text.setText("{\n" +
                            "    \"uploadResponseCode\": \""+response.body().getUploadResponseCode()+"\",\n" +
                            "    \"userId\": \""+response.body().getUserid()+"\",\n" +
                            "    \"number\": "+response.body().getNumber()+",\n" +
                            "    \"names\": \""+response.body().getNames()+"\",\n" +
                            "    \"message\": \""+response.body().getMessage()+"\"\n" +
                            "}");
                }
            }

            @Override
            public void onFailure(Call<TripCloud> call, Throwable t) {
                textView.setText("Error found is " + t.getMessage());
            }
        });
    }
    private void getNameTrip(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(UploadActivity.this, "No data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                tripName.add(cursor.getString(1));
            }
        }
    }

    private String setJson(List<String> tripName){
        String json1 = "";
        for(int i = 0; i<tripName.size()-1;i++){
            String item = "{\n" +
                    "      \"name\": \""+tripName.get(i)+"\"\n" +
                    "    }";
            json1 = json1 + item +",\n";
        }
        json1 = json1 + "{\n" +
                "      \"name\": \""+tripName.get(tripName.size()-1)+"\"\n" +
                "    }";
        return json1;
    }
    private String finalJson(String json){
        return "{\n" +
                "  \"userId\": \"wm123\",\n" +
                "  \"detailList\": [\n" +
                json+"\n" +
                "  ]\n" +
                "}";
    }
}