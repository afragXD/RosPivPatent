package com.example.rospivpatent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnLoup, btnMenu;
    private EditText editChoose;
    private LinearLayout layoutSearch;
    private TextView textView;
    ArrayList<SearchClass> list;

    DrawerLayout drawerLayout;
    private LinearLayout simpleSearch, advancedSearch, AISearch, options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        click();
    }

    private void init(){
        btnLoup = findViewById(R.id.btnLoup);
        editChoose = findViewById(R.id.editChoose);
        textView = findViewById(R.id.textView);
        layoutSearch = findViewById(R.id.layoutSearch);
        btnMenu = findViewById(R.id.btnMenu);
        drawerLayout = findViewById(R.id.drawerLayout);
        simpleSearch = findViewById(R.id.simpleSearch);
        advancedSearch = findViewById(R.id.advancedSearch);
        AISearch = findViewById(R.id.AISearch);
        options = findViewById(R.id.options);

        list = new ArrayList<>();
    }

    private void click(){
        btnLoup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post(editChoose.getText().toString(), 5);
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        simpleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SimpleSearchActivity.class);
                startActivity(intent);
                //finish();
            }
        });
        advancedSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        AISearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void post(String input, Integer outCount){
        JSONObject postData = new JSONObject();
        try {
            postData.put("qn", input);
            postData.put("limit", outCount);
            postData.put("pre_tag", "<font color=\"#E30613\">");
            postData.put("post_tag", "</font>");
        } catch (JSONException e) {
            Log.d("MyLog", "FF");
        }

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.URL),postData , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Log.d("MyLog",response.toString());
                    for (int index = 0; index < outCount; index++){
                        SearchClass buf = new SearchClass();
                        buf.setTitle(response.getJSONArray("hits").getJSONObject(index).getJSONObject("snippet").getString("title"));
                        buf.setDescription(response.getJSONArray("hits").getJSONObject(index).getJSONObject("snippet").getString("description"));
                        buf.setFullname("МПК " + response.getJSONArray("hits").getJSONObject(1).getJSONObject("snippet").getJSONObject("classification").getString("ipc"));

                        buf.setKind(response.getJSONArray("hits").getJSONObject(index).getJSONObject("common").getString("kind"));
                        buf.setPublication_date(response.getJSONArray("hits").getJSONObject(index).getJSONObject("common").getString("publication_date"));

                        buf.setPublishing_office(response.getJSONArray("hits").getJSONObject(index).getJSONObject("common").getString("publishing_office"));
                        buf.setDocument_number(response.getJSONArray("hits").getJSONObject(index).getJSONObject("common").getString("document_number"));
                        list.add(index, buf);
                    }
                    Intent intent = new Intent(MainActivity.this, SearchingResultsActivity.class);
                    startActivity(intent);
                    Single.getInstance().list = list;
                    //Название
                    //Log.d("MyLog", response.getJSONArray("hits").getJSONObject(1).getJSONObject("snippet").getString("title"));
                    //Описание
                    //Log.d("MyLog", response.getJSONArray("hits").getJSONObject(0).getJSONObject("snippet").getString("description"));
                    //МПК
                    //Log.d("MyLog", response.getJSONArray("hits").getJSONObject(1).getJSONObject("snippet").getJSONObject("classification").getString("ipc"));
                    //kind
                    //Log.d("MyLog", response.getJSONArray("hits").getJSONObject(0).getJSONObject("common").getString("kind"));
                    //publication_date
                    //Log.d("MyLog", response.getJSONArray("hits").getJSONObject(0).getJSONObject("common").getString("publication_date"));
                    //publishing_office
                    //Log.d("MyLog", response.getJSONArray("hits").getJSONObject(0).getJSONObject("common").getString("publishing_office"));
                    //document_number
                    //Log.d("MyLog", response.getJSONArray("hits").getJSONObject(0).getJSONObject("common").getString("document_number"));
                } catch (JSONException e) {
                    Log.d("MyLog", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MyLog", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + getString(R.string.SECURITY_API_KEY));
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }
}