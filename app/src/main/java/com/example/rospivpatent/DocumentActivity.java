package com.example.rospivpatent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class DocumentActivity extends AppCompatActivity {

    private ImageButton btnMenu;
    DrawerLayout drawerLayout;
    private LinearLayout simpleSearch, advancedSearch, AISearch, options;
    private TextView textTitle, MPKText, textID, textDate, textNumber, textInventor, textReferat, textLink, textDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        init();
        click();
        setTexts();
    }

    private void init(){
        btnMenu = findViewById(R.id.btnMenu);
        drawerLayout = findViewById(R.id.drawerLayout);
        simpleSearch = findViewById(R.id.simpleSearch);
        advancedSearch = findViewById(R.id.advancedSearch);
        AISearch = findViewById(R.id.AISearch);
        options = findViewById(R.id.options);

        textTitle = findViewById(R.id.textTitle);
        MPKText = findViewById(R.id.MPKText);
        textID = findViewById(R.id.textID);
        textDate = findViewById(R.id.textDate);
        textNumber = findViewById(R.id.textNumber);
        textInventor = findViewById(R.id.textInventor);
        textReferat = findViewById(R.id.textReferat);
        textLink = findViewById(R.id.textLink);
        textDescription = findViewById(R.id.textDescription);
    }

    private void click(){
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        simpleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DocumentActivity.this, SimpleSearchActivity.class);
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
    private void setTexts(){
        textTitle.setText(Html.fromHtml(Single.getInstance().thisElement.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        MPKText.setText(Html.fromHtml("<strong>?????? </strong>" + Single.getInstance().thisElement.getFullname(), Html.FROM_HTML_MODE_LEGACY));
        textID.setText(Html.fromHtml("<strong>?????????????????????????? </strong>" + Single.getInstance().thisElement.getId(), Html.FROM_HTML_MODE_LEGACY));
        textDate.setText(Html.fromHtml("<strong>???????? ???????????????????? </strong>" + Single.getInstance().thisElement.getPublication_date(), Html.FROM_HTML_MODE_LEGACY));
        textNumber.setText(Html.fromHtml("<strong>?????????? </strong>" + Single.getInstance().thisElement.getPublishing_office() + " " +
                        Single.getInstance().thisElement.getDocument_number() + " " + Single.getInstance().thisElement.getKind(),
                Html.FROM_HTML_MODE_LEGACY));
        textInventor.setText(Html.fromHtml("<strong>???????????? ???????????????????? </strong>" + Single.getInstance().thisElement.getInventor(), Html.FROM_HTML_MODE_LEGACY));
        textDescription.setText(Html.fromHtml(Single.getInstance().thisElement.getDescription(), Html.FROM_HTML_MODE_LEGACY));
        textReferat.setText(Single.getInstance().thisElement.getReferat());

        RequestQueue requestQueue1 = Volley.newRequestQueue(DocumentActivity.this);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.URL2) + Single.getInstance().thisElement.getId(),null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Log.d("MyLog", response.getJSONObject("abstract").getJSONObject("en").toString());
                    String allText = response.getJSONObject("abstract").getString("en");
                    String uitText = allText.replaceAll("</p></pat:Abstract>.*", "");
                    uitText = uitText.substring(uitText.lastIndexOf(">") + 1);
                    textReferat.setText(uitText);
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

        textLink.setText(Html.fromHtml("<a href=\\\"https://searchplatform.rospatent.gov.ru/doc/" + Single.getInstance().thisElement.getId() + "\\\">???????????? ???????????? ??????????????????" + "</a>", Html.FROM_HTML_MODE_LEGACY));
        textLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://searchplatform.rospatent.gov.ru/doc/" + Single.getInstance().thisElement.getId()));
                startActivity(browserIntent);
            }
        });
    }
}