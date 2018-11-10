package com.wallmark;


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
import android.widget.Toast;

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
public class AllPhotoFragment extends Fragment{

    private AllPhotoRecyclerViewHolder myViewHolder;
    private List<UrlDetails> seriesList;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean check = false;
    List<Photo> photo;

    public AllPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.all_photo_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.all_photo_recyclerView);
        recyclerView.setHasFixedSize(true);
        seriesList = new ArrayList<>();
        myViewHolder = new AllPhotoRecyclerViewHolder(seriesList,getActivity(),"ALL_PHOTO");

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myViewHolder);

        swipeRefreshLayout.setEnabled(false);
        prepareForData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    myViewHolder.clear();
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

        Call<Model> call = retroApi.getSerch();

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                photo = response.body().getPhotos().getPhoto();
                for(int i=0; i < photo.size(); i++){
                    int farm = photo.get(i).getFarm();
                    String server = photo.get(i).getServer();
                    String id = photo.get(i).getId();
                    String secret = photo.get(i).getSecret();
                    String name = photo.get(i).getTitle();
                    String url = "http://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+"_b.jpg";
                    seriesList.add(new UrlDetails(url,id,name));
                    myViewHolder.notifyDataSetChanged();
                }
                swipeRefreshLayout.setEnabled(true);
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(getActivity(), "Some thing Wrong! Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
