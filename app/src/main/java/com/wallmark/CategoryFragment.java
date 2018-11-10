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

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private CategoryRecyclerViewHolder myViewHolder;
    private List<UrlDetails> seriesList;
    Context context;
    List<Photoset> photo;
    SwipeRefreshLayout swipeRefreshLayout;
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        context = container.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.category_recyclerView);
        recyclerView.setHasFixedSize(true);
        seriesList = new ArrayList<>();
        myViewHolder = new CategoryRecyclerViewHolder(seriesList,getActivity());

        swipeRefreshLayout = view.findViewById(R.id.categorySwipe);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myViewHolder);

        prepareForData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myViewHolder.clear();
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
            public void onResponse(Call<Category> call, retrofit2.Response<Category> response) {
                photo = response.body().getPhotosets().getPhotoset();
                for(int i =0;i<photo.size(); i++){
                    int farm = photo.get(0).getFarm();
                    String server = photo.get(i).getServer();
                    String primary = photo.get(i).getPrimary();
                    String secret = photo.get(i).getSecret();
                    String id = photo.get(i).getId();
                    String name = photo.get(i).getTitle().getContent();
                    String url = "http://farm"+farm+".staticflickr.com/"+server+"/"+primary+"_"+secret+".jpg";
                    seriesList.add(new UrlDetails(url,id,name));
                    myViewHolder.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });



    }

}
