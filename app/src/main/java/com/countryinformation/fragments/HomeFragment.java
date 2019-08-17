package com.countryinformation.fragments;

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
import com.countryinformation.HomeActivity;
import com.countryinformation.R;
import com.countryinformation.adapter.CountryRecyclerAdapter;
import com.countryinformation.adapter.OnCountryListener;
import com.countryinformation.glide.GlideApp;
import com.countryinformation.glide.SvgSoftwareLayerSetter;
import com.countryinformation.model.CountryInfo;
import com.countryinformation.viewmodel.MainViewModel;
import com.countryinformation.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class HomeFragment extends BaseFragment implements OnCountryListener {

    public static final String TAG = "HomeFragment";
    @Inject
    ViewModelFactory viewModelFactory;

    private MainViewModel mainViewModel;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private CountryRecyclerAdapter mAdapter;
    private RelativeLayout progressBar, parentErrorView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.country_list);
        mSearchView = view.findViewById(R.id.search_view);
        progressBar = view.findViewById(R.id.progress_bar_parent);
        parentErrorView = view.findViewById(R.id.parent_error);

        initRecyclerView(initGlide());
        initSearchView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainViewModel = new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);
        subscribeObservers();
    }

    private void initRecyclerView(final RequestBuilder<PictureDrawable> requestBuilder) {
        mAdapter = new CountryRecyclerAdapter(this, requestBuilder);
        DividerItemDecoration itemDecor = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecor);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    private RequestBuilder<PictureDrawable> initGlide() {
        return GlideApp.with(this)
                .as(PictureDrawable.class)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .transition(withCrossFade())
                .listener(new SvgSoftwareLayerSetter());
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

    private void subscribeObservers() {
        mainViewModel.getCountries().observe(this, resource -> {
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
    }

    @Override
    public void onCountryClick(int position) {
        mSearchView.setQuery("", false);
        CountryInfo selectedCountry = mAdapter.getSelectedCountry(position);
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((HomeActivity) getActivity()).show(selectedCountry);
        }
    }
}
