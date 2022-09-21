package com.example.rospivpatent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        holder.textTitle.setText(searchClass.getTitle());
        holder.textFullname.setText(searchClass.getFullname());
        holder.textDate.setText(searchClass.getDate());
        holder.textDocument.setText(searchClass.getDocument());
        holder.textDescription.setText(searchClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textFullname, textDate, textDocument, textDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textFullname = itemView.findViewById(R.id.textFullname);
            textDate = itemView.findViewById(R.id.textDate);
            textDocument = itemView.findViewById(R.id.textDocument);
            textDescription = itemView.findViewById(R.id.textDescription);
        }
    }

}
