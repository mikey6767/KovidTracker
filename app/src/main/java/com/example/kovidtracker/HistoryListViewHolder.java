package com.example.kovidtracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryListViewHolder extends RecyclerView.ViewHolder {

    public TextView location, date;
    public HistoryListViewHolder(@NonNull View itemView) {
        super(itemView);

        location = itemView.findViewById(R.id.his_location);
        date = itemView.findViewById(R.id.his_datetime);
    }
}
