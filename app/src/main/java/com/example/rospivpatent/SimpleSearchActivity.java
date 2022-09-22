package com.example.rospivpatent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimpleSearchActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private LinearLayout simpleSearch, advancedSearch, AISearch, options;
    private ImageButton btnLoupM, btnMenu;
    private EditText editChoose;

    ArrayList<SearchClass> list;
    String[] countries = { "Россия и страны СНГ", "Минимум РСТ", "Страны с малым потентным фондом"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_search);
        init();
        click();
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String item = (String)parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }
    private void init(){
        btnLoupM = findViewById(R.id.btnLoupSimple);
        editChoose = findViewById(R.id.editChooseSimple);
        btnMenu = findViewById(R.id.btnMenu);
        drawerLayout = findViewById(R.id.drawerLayout);
        simpleSearch = findViewById(R.id.simpleSearch);
        advancedSearch = findViewById(R.id.advancedSearch);
        AISearch = findViewById(R.id.AISearch);
        options = findViewById(R.id.options);

        list = new ArrayList<>();
    }
    private void click(){
        btnMenu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    });
        btnLoupM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post(editChoose.getText().toString());
            }
        });
        simpleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
    private void post(String input){
        JSONObject postData = new JSONObject();
        try {
            JSONObject filter = new JSONObject();

            postData.put("qn", "beer");
            postData.put("limit", Single.getInstance().count);
            postData.put("pre_tag", "<font color=\"#E30613\">");
            postData.put("post_tag", "</font>");

            JSONArray values = new JSONArray();
            values.put("200100594");
            filter.put("number", values);


            JSONArray values2 = new JSONArray();
            values2.put("20020702");
            filter.put("date_published", values2);

            postData.put("filter", filter);
            Log.d("MyLog", Single.getInstance().count.toString());
            Log.d("MyLog", postData.toString());
        } catch (JSONException e) {
            Log.d("MyLog", "FF");
        }

        RequestQueue requestQueue = Volley.newRequestQueue(SimpleSearchActivity.this);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.URL),postData , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Log.d("MyLog", response.getJSONArray("hits").getJSONObject(0).toString());
                    for (int index = 0; index < Single.getInstance().count; index++){
                        SearchClass buf = new SearchClass();
                        buf.setTitle(response.getJSONArray("hits").getJSONObject(index).getJSONObject("snippet").getString("title"));
                        buf.setDescription(response.getJSONArray("hits").getJSONObject(index).getJSONObject("snippet").getString("description"));
                        buf.setFullname(response.getJSONArray("hits").getJSONObject(1).getJSONObject("snippet").getJSONObject("classification").getString("ipc"));

                        buf.setKind(response.getJSONArray("hits").getJSONObject(index).getJSONObject("common").getString("kind"));
                        buf.setPublication_date(response.getJSONArray("hits").getJSONObject(index).getJSONObject("common").getString("publication_date"));

                        buf.setPublishing_office(response.getJSONArray("hits").getJSONObject(index).getJSONObject("common").getString("publishing_office"));
                        buf.setDocument_number(response.getJSONArray("hits").getJSONObject(index).getJSONObject("common").getString("document_number"));
                        buf.setId(response.getJSONArray("hits").getJSONObject(index).getString("id"));
                        try {
                            //Log.d("MyLog",response.getJSONArray("hits").getJSONObject(1).getJSONObject("snippet").getString("inventor"));
                            buf.setInventor(response.getJSONArray("hits").getJSONObject(index).getJSONObject("snippet").getString("inventor"));
                        } catch (JSONException e){
                            buf.setInventor("-");
                        }

                        RequestQueue requestQueue1 = Volley.newRequestQueue(SimpleSearchActivity.this);
                        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.URL2) + buf.getId(),null , new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    //Log.d("MyLog", response.getJSONObject("abstract").getJSONObject("en").toString());
                                    String allText = response.getJSONObject("abstract").getString("en");
                                    String uitText = allText.replaceAll("</p></pat:Abstract>.*", "");
                                    uitText = uitText.substring(uitText.lastIndexOf(">") + 1);
                                    buf.setReferat(uitText);
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
                        requestQueue1.add(stringRequest);

                        list.add(index, buf);
                    }

                    Intent intent = new Intent(SimpleSearchActivity.this, SearchingResultsActivity.class);
                    startActivity(intent);
                    Single.getInstance().list = list;
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