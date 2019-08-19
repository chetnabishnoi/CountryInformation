package com.countryinformation.adapter;

import android.graphics.drawable.PictureDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestBuilder;
import com.countryinformation.R;
import com.countryinformation.model.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private static final int COUNTRY_TYPE = 1;
    private List<Country> countryList;
    private List<Country> filteredList;

    private OnCountryListener mOnCountryListener;
    private RequestBuilder<PictureDrawable> requestBuilder;

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Country> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(CountryRecyclerAdapter.this.filteredList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Country item : CountryRecyclerAdapter.this.filteredList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                countryList.clear();
                countryList.addAll((List<Country>) results.values);
                notifyDataSetChanged();
            }
        }
    };

    public CountryRecyclerAdapter(OnCountryListener mOnCountryListener, RequestBuilder<PictureDrawable> requestBuilder) {
        this.mOnCountryListener = mOnCountryListener;
        this.requestBuilder = requestBuilder;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_country_adapter, viewGroup, false);
        return new CountryViewHolder(view, mOnCountryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        Country country = countryList.get(position);
        CountryViewHolder countryViewHolder = (CountryViewHolder) viewHolder;
        requestBuilder.load(country.getFlag()).into(countryViewHolder.image);
        countryViewHolder.name.setText(country.getName());
        countryViewHolder.capital.setText(country.getCapital());
    }

    @Override
    public int getItemViewType(int position) {
        return COUNTRY_TYPE;
    }

    @Override
    public int getItemCount() {
        if (countryList != null) {
            return countryList.size();
        }
        return 0;
    }

    public Country getSelectedCountry(int position) {
        if (countryList != null) {
            if (countryList.size() > 0) {
                return countryList.get(position);
            }
        }
        return null;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public void setCountries(List<Country> countryList) {
        this.countryList = countryList;
        this.filteredList = new ArrayList<>(countryList);
        notifyDataSetChanged();
    }
}















