package com.countryinformation.home;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestBuilder;
import com.countryinformation.R;
import com.countryinformation.adapter.CountryRecyclerAdapter;
import com.countryinformation.adapter.OnCountryListener;
import com.countryinformation.home.viewmodel.HomeViewModel;
import com.countryinformation.model.Country;
import com.countryinformation.utils.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * This fragment shows the list of all the countries available.
 * It fetches the list from api and shows the flag and country name.
 * User can also search by the country name on this page.
 */
public class HomeFragment extends DaggerFragment implements OnCountryListener {

    static final String TAG = "HomeFragment";

    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    RequestBuilder<PictureDrawable> glide;

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private CountryRecyclerAdapter mAdapter;
    private RelativeLayout progressBar, parentErrorView;
    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.country_list);
        mSearchView = view.findViewById(R.id.search_view);
        progressBar = view.findViewById(R.id.progress_bar_parent);
        parentErrorView = view.findViewById(R.id.parent_error);

        initRecyclerView(glide);
        initSearchView();
        homeViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);
        subscribeObservers();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void initRecyclerView(final RequestBuilder<PictureDrawable> requestBuilder) {
        mAdapter = new CountryRecyclerAdapter(this, requestBuilder);
        DividerItemDecoration itemDecor = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecor);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    private void initSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    //Subscribe to the country list observable from view model
    private void subscribeObservers() {
        homeViewModel.observeCountryList().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        progressBar.setVisibility(View.VISIBLE);
                        parentErrorView.setVisibility(View.GONE);
                        break;
                    case SUCCESS:
                        progressBar.setVisibility(View.GONE);
                        parentErrorView.setVisibility(View.GONE);
                        mAdapter.setCountries(resource.data);
                        break;
                    case ERROR:
                        progressBar.setVisibility(View.GONE);
                        parentErrorView.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        homeViewModel.fetchCountries();
    }

    /**
     * Opens the detail page for the selected country
     *
     * @param position that has been clicked
     */
    @Override
    public void onCountryClick(int position) {
        mSearchView.setQuery("", false);
        Country selectedCountry = mAdapter.getSelectedCountry(position);
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((HomeActivity) getActivity()).show(selectedCountry);
        }
    }
}
