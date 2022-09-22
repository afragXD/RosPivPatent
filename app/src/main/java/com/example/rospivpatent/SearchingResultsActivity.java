package com.example.rospivpatent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchingResultsActivity extends AppCompatActivity {
    private ImageButton btnLoup, btnMenu, btnLoupSimple;
    private EditText editChooseSimple;
    DrawerLayout drawerLayout;
    private LinearLayout simpleSearch, advancedSearch, AISearch, options;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private ArrayList<SearchClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_results);

        init();
        click();
        createRecyclerView();
    }
    private void init(){
        drawerLayout = findViewById(R.id.drawerLayout);
        btnMenu = findViewById(R.id.btnMenu);
        recyclerView = findViewById(R.id.recyclerView);
        editChooseSimple = findViewById(R.id.editChooseSimple);
        btnLoupSimple = findViewById(R.id.btnLoupSimple);
        simpleSearch = findViewById(R.id.simpleSearch);
        advancedSearch = findViewById(R.id.advancedSearch);
        AISearch = findViewById(R.id.AISearch);
        options = findViewById(R.id.options);
    }
    private void click(){
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        btnLoupSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                searchAdapter.notifyDataSetChanged();
                post(editChooseSimple.getText().toString());
            }
        });
        simpleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchingResultsActivity.this, SimpleSearchActivity.class);
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
    private void createRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        list = Single.getInstance().list;
        searchAdapter = new SearchAdapter(this, list);
        recyclerView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
    }
    private void post(String input){
        JSONObject postData = new JSONObject();
        try {
            postData.put("qn", input);
            postData.put("limit", Single.getInstance().count);
            postData.put("pre_tag", "<font color=\"#E30613\">");
            postData.put("post_tag", "</font>");
        } catch (JSONException e) {
            Log.d("MyLog", "FF");
        }

        RequestQueue requestQueue = Volley.newRequestQueue(SearchingResultsActivity.this);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.URL),postData , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Log.d("MyLog",response.toString());
                    for (int index = 0; index < Single.getInstance().count; index++){
                        SearchClass buf = new SearchClass();
                        buf.setTitle(response.getJSONArray("hits").getJSONObject(index).getJSONObject("snippet").getString("title"));
                        buf.setDescription(response.getJSONArray("hits").getJSONObject(index).getJSONObject("snippet").getString("description"));
                        buf.setFullname("МПК " + response.getJSONArray("hits").getJSONObject(1).getJSONObject("snippet").getJSONObject("classification").getString("ipc"));

                        buf.setKind(response.getJSONArray("hits").getJSONObject(index).getJSONObject("common").getString("kind"));
                        buf.setPublication_date(response.getJSONArray("hits").getJSONObject(index).getJSONObject("common").getString("publication_date"));

                        buf.setPublishing_office(response.getJSONArray("hits").getJSONObject(index).getJSONObject("common").getString("publishing_office"));
                        buf.setDocument_number(response.getJSONArray("hits").getJSONObject(index).getJSONObject("common").getString("document_number"));
                        list.add(index, buf);
                        searchAdapter.notifyItemInserted(index);
                    }
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