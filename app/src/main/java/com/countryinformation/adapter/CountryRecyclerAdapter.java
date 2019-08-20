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
    private List<Country> countryListFiltered;

    private OnCountryListener mOnCountryListener;
    private RequestBuilder<PictureDrawable> requestBuilder;

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
        Country country = countryListFiltered.get(position);
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
        if (countryListFiltered != null) {
            return countryListFiltered.size();
        }
        return 0;
    }

    public Country getSelectedCountry(int position) {
        if (countryListFiltered != null) {
            if (countryListFiltered.size() > 0) {
                return countryListFiltered.get(position);
            }
        }
        return null;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Country> filterList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filterList.addAll(countryList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Country item : countryList) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filterList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filterList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null) {
                    countryListFiltered.clear();
                    countryListFiltered.addAll((List<Country>) results.values);
                    notifyDataSetChanged();
                }
            }
        };
    }

    public void setCountries(List<Country> countryList) {
        this.countryListFiltered = countryList;
        this.countryList = new ArrayList<>(countryList);
        notifyDataSetChanged();
    }
}















