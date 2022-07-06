package com.example.kovidtracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoseListViewHolder extends RecyclerView.ViewHolder {

    public TextView brand, batch, facility, date, dose;
    public DoseListViewHolder(@NonNull View itemView) {
        super(itemView);

        dose = itemView.findViewById((R.id.tv_dose));
        brand = itemView.findViewById(R.id.tv_brand);
        batch = itemView.findViewById(R.id.tv_batch);
        facility = itemView.findViewById(R.id.tv_facility);
        date = itemView.findViewById(R.id.tv_date);
    }
}
