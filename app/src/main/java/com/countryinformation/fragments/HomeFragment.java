package com.countryinformation.fragments;

import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestBuilder;
import com.countryinformation.R;
import com.countryinformation.adapter.CountryRecyclerAdapter;
import com.countryinformation.adapter.OnCountryListener;
import com.countryinformation.glide.GlideApp;
import com.countryinformation.glide.GlideRequest;
import com.countryinformation.glide.SvgSoftwareLayerSetter;
import com.countryinformation.model.CountryInfo;
import com.countryinformation.network.Resource;
import com.countryinformation.utils.VerticalSpacingItemDecorator;
import com.countryinformation.viewmodel.MainViewModel;
import com.countryinformation.viewmodel.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class HomeFragment extends BaseFragment implements OnCountryListener {

    @Inject
    ViewModelFactory viewModelFactory;

    private MainViewModel mainViewModel;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private CountryRecyclerAdapter mAdapter;
    private RelativeLayout progressBar, parentErrorView;
    private static final String TAG = "HomeFragment";
    private NavController navController;

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
        navController = Navigation.findNavController(view);

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        initRecyclerView(initGlide());
        subscribeObservers();
        initSearchView();
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchMovies();
    }

    private GlideRequest<PictureDrawable> initGlide() {
        return GlideApp.with(this)
                .as(PictureDrawable.class)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .transition(withCrossFade())
                .listener(new SvgSoftwareLayerSetter());
    }

    private void fetchMovies() {
        mainViewModel.fetchMovies();
    }

    private void subscribeObservers() {
        mainViewModel.observeCountryList().observe(this, new Observer<Resource<List<CountryInfo>>>() {
            @Override
            public void onChanged(Resource<List<CountryInfo>> resource) {
                if (resource != null) {
                    switch (resource.status) {
                        case LOADING:
                            progressBar.setVisibility(View.VISIBLE);
                            parentErrorView.setVisibility(View.GONE);
                            break;
                        case SUCCESS:
                            Log.d(TAG, "onChanged: inside success");
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
            }
        });
    }

    private void initRecyclerView(final RequestBuilder<PictureDrawable> requestBuilder) {
        mAdapter = new CountryRecyclerAdapter(this, requestBuilder);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(itemDecorator);
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

    @Override
    public void onCountryClick(int position) {
        mSearchView.setQuery("", false);
        CountryInfo selectedCountry = mAdapter.getSelectedCountry(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailFragment.ARG_COUNTRY_DETAIL, selectedCountry);
        navController.navigate(R.id.action_homeFragment_to_detailFragment, bundle);
    }
}
