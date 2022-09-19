package com.example.rospivpatent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button button;

    String URL = "https://searchplatform.rospatent.gov.ru/patsearch/v0.2/search";
    String API_KEY = "193692a8f81a4d9286964658a811141c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api1();
    }
    private void api1(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MyLog", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MyLog", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap header = new HashMap();
                header.put("Authorization", "Bearer " + API_KEY);
                header.put("Content-Type", "application/json");
                header.put("limit", "1");
                header.put("qn", "пиво");
                return header;
            }
        };
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestQueue.add(stringRequest);
            }
        });
    }
    private void api2(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MyLog", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MyLog", error.toString());
            }
        })
        {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap header = new HashMap();
                header.put("Authorization", "Bearer " + API_KEY);
                header.put("Content-Type", "application/json");
                header.put("limit", "1");
                header.put("qn", "пиво");
                return header;
            }
        };
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestQueue.add(request);
            }
        });
    }
}