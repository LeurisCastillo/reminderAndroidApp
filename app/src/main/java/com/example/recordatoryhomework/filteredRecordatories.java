package com.example.recordatoryhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class filteredRecordatories extends AppCompatActivity {


    private String URL_FILTER_DAY = "http://192.168.0.105/recordatorydb/filter.php";

    ListView list_view_filtered;
    List<String> recordatoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_recordatories);

        list_view_filtered = findViewById(R.id.list_view_filtered);
        recordatoriesList = new ArrayList<>();
        filterByDay(URL_FILTER_DAY);
    }

    void filterByDay(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {

                    try {

                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++){
                            JSONObject recordatory = array.getJSONObject(i);

                            recordatoriesList.add(recordatory.getString("message"));
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_custom, recordatoriesList);
                            list_view_filtered.setAdapter(adapter);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> Toast.makeText(filteredRecordatories.this, error.toString(), Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}