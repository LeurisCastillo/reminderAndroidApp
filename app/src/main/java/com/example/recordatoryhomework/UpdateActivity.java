package com.example.recordatoryhomework;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    private String URL = "http://192.168.0.105/recordatorydb/getdate.php";
    private String URL_UPDATE = "http://192.168.0.105/recordatorydb/update.php";
    private String URL_DELETE = "http://192.168.0.105/recordatorydb/delete.php";

    EditText recordatory_update, date_update;
    Button update_button, delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");

        // Edit text
        recordatory_update = findViewById(R.id.recordatory_update);
        date_update = findViewById(R.id.date_update);

        // Inserting the values in edit text
        recordatory_update.setText(message);
        getDataFromMessage(URL, recordatory_update.getText().toString());

        //Buttons update & delete
        update_button = findViewById(R.id.update_button);
        update_button.setOnClickListener(view -> {
            updateRecordatory(URL_UPDATE, recordatory_update.getText().toString(), date_update.getText().toString());
            finish();
        });

        delete_button = findViewById(R.id.delete_button);
        delete_button.setOnClickListener(view -> {
            deleteRecordatory(URL_DELETE, recordatory_update.getText().toString());
            finish();
        });
    }

    void getDataFromMessage(String url, String message){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                response -> {

                try{

                    JSONObject object = new JSONObject(response);
                    String data = object.getString("date");
                    date_update.setText(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                }, error -> Toast.makeText(UpdateActivity.this, error.toString(), Toast.LENGTH_SHORT).show()){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("message", message);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    void updateRecordatory(String url, String message, String date){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,

                response -> Toast.makeText(UpdateActivity.this, "Recordatory updated successfully!", Toast.LENGTH_SHORT).show(),

                error -> Toast.makeText(UpdateActivity.this, error.toString(), Toast.LENGTH_SHORT).show()){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("message", message);
                params.put("date", date);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    void deleteRecordatory(String url, String message){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,

                response -> Toast.makeText(UpdateActivity.this, "Recordatory deleted successfully!", Toast.LENGTH_SHORT).show(),

                error -> Toast.makeText(UpdateActivity.this, error.toString(), Toast.LENGTH_SHORT).show()){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("message", message);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}