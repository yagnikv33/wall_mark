package com.wallmark;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    @BindView(R.id.category_recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.categorySwipe)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.category_fragment_progressbar)
    ProgressBar progressBar;

    private CategoryRecyclerViewHolder myViewHolder;
    private List<UrlDetails> seriesList;
    Context context;
    List<Photoset> photo;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this,view);

        recyclerView.setHasFixedSize(true);
        seriesList = new ArrayList<>();
        myViewHolder = new CategoryRecyclerViewHolder(seriesList,getActivity());



        swipeRefreshLayout.setEnabled(false);
        prepareForData();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myViewHolder);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myViewHolder.clear();
                progressBar.setVisibility(ProgressBar.VISIBLE);
                swipeRefreshLayout.setEnabled(false);
                prepareForData();
                myViewHolder.addAll(seriesList);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void prepareForData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/services/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        RetroApi retroApi = retrofit.create(RetroApi.class);

        Call<Category> call = retroApi.getCategoryList();

        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(@NonNull Call<Category> call, @NonNull retrofit2.Response<Category> response) {
                assert response.body() != null;
                photo = response.body().getPhotosets().getPhotoset();
                for (Photoset list: photo) {
                    int farm = list.getFarm();
                    String server = list.getServer();
                    String primary = list.getPrimary();
                    String secret = list.getSecret();
                    String id = list.getId();
                    String name = list.getTitle().getContent();
                    String url = "http://farm"+farm+".staticflickr.com/"+server+"/"+primary+"_"+secret+".jpg";
                    seriesList.add(new UrlDetails(url,id,name));
                    myViewHolder.notifyDataSetChanged();
                    progressBar.setVisibility(ProgressBar.GONE);
                }
                swipeRefreshLayout.setEnabled(true);
            }

            @Override
            public void onFailure(@NonNull Call<Category> call, @NonNull Throwable t) {

            }
        });



    }

}
