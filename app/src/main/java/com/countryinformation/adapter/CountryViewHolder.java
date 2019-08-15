package com.countryinformation.adapter;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.countryinformation.R;


public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView name, capital;
    AppCompatImageView image;
    private OnCountryListener onCountryListener;

    CountryViewHolder(@NonNull View itemView, OnCountryListener onCountryListener) {
        super(itemView);
        this.onCountryListener = onCountryListener;
        name = itemView.findViewById(R.id.country_name);
        image = itemView.findViewById(R.id.country_flag);
        capital = itemView.findViewById(R.id.capital);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onCountryListener.onCountryClick(getAdapterPosition());
    }
}





