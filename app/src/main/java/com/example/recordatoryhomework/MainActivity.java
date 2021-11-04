package com.example.recordatoryhomework;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String URL = "http://192.168.0.105/recordatorydb/buscar1.php";

    FloatingActionButton add_button;
    Button filter_day_button;
    ListView list_view;

    List<String> recordatoriesList;
//    RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_view = findViewById(R.id.list_view);
        add_button = findViewById(R.id.add_button);
        filter_day_button = findViewById(R.id.filter_day_button);

        recordatoriesList = new ArrayList<>();

//        request = Volley.newRequestQueue(this);
        getRecordatorys(URL);

        add_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivityForResult(intent, 1);
        });

        filter_day_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, filteredRecordatories.class);
            startActivity(intent);
        });

        list_view.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            intent.putExtra("message", ((TextView) view).getText());
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void getRecordatorys(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                response -> {

                try {

                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++){
                        JSONObject recordatory = array.getJSONObject(i);

                        recordatoriesList.add(recordatory.getString("message"));
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_custom, recordatoriesList);
                        list_view.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                }, error -> Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}