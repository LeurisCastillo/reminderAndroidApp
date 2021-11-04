package com.example.recordatoryhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    private String URL = "http://192.168.0.105/recordatorydb/add.php";

    EditText recordatory_edit_text, date_edit_text;
    Button insert_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        recordatory_edit_text = findViewById(R.id.recordatory_edit_text);
        date_edit_text = findViewById(R.id.date_edit_text);
        insert_button = findViewById(R.id.insert_button);

        insert_button.setOnClickListener(view -> {
            addRecordatory(URL, recordatory_edit_text.getText().toString(), date_edit_text.getText().toString());
            finish();
        });
    }

    void addRecordatory(String url, String message, String date){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {

                    if (!response.isEmpty()){

                        Toast.makeText(AddActivity.this, "Recordatory added successfully!", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(AddActivity.this, "Recordatory added failed", Toast.LENGTH_SHORT).show();
                    }

                }, error -> Toast.makeText(AddActivity.this, error.toString(), Toast.LENGTH_LONG).show()){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("message", message);
                params.put("date", date);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AddActivity.this);
        requestQueue.add(stringRequest);
    }
}