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
import com.countryinformation.model.CountryInfo;

import java.util.ArrayList;
import java.util.List;

public class CountryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private static final int COUNTRY_TYPE = 1;
    private List<CountryInfo> countryList;
    private List<CountryInfo> countryListFull;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        requestBuilder.load(countryList.get(i).getFlag()).into(((CountryViewHolder) viewHolder).image);
        ((CountryViewHolder) viewHolder).name.setText(countryList.get(i).getName());
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

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CountryInfo> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(countryListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CountryInfo item : countryListFull) {
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
                countryList.addAll(((List) results.values));
                notifyDataSetChanged();
            }
        }
    };

    public CountryInfo getSelectedCountry(int position) {
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

    public void setCountries(List<CountryInfo> countryList) {
        this.countryList = countryList;
        this.countryListFull = new ArrayList<>(countryList);
        notifyDataSetChanged();
    }
}















