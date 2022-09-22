package com.example.rospivpatent;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    Context context;
    ArrayList<SearchClass> list;

    public SearchAdapter(Context context, ArrayList<SearchClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_serch_alement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchClass searchClass = list.get(position);
        holder.textTitle.setText(Html.fromHtml(searchClass.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        holder.textFullname.setText(Html.fromHtml("МПК " +  searchClass.getFullname(), Html.FROM_HTML_MODE_LEGACY));

        holder.textDate.setText(Html.fromHtml(searchClass.getKind() + " " + searchClass.getPublication_date(), Html.FROM_HTML_MODE_LEGACY));
        holder.textDocument.setText(Html.fromHtml("Документ " + searchClass.getPublishing_office() + " " + searchClass.getDocument_number(), Html.FROM_HTML_MODE_LEGACY));

        holder.textDescription.setText(Html.fromHtml(searchClass.getDescription(), Html.FROM_HTML_MODE_LEGACY));

        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Single.getInstance().thisElement = list.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, DocumentActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textFullname, textDate, textDocument, textDescription;
        MaterialCardView materialCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textFullname = itemView.findViewById(R.id.textFullname);
            textDate = itemView.findViewById(R.id.textDate);
            textDocument = itemView.findViewById(R.id.textDocument);
            textDescription = itemView.findViewById(R.id.textDescription);
            materialCardView = itemView.findViewById(R.id.materialCardView);
        }
    }

}
