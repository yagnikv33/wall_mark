package com.wallmark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayAlbumPhotos extends AppCompatActivity {

    @BindView(R.id.album_recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.albumPhotoSwipe)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.display_album_photo_progressbar)
    ProgressBar progressBar;

    private AllPhotoRecyclerViewHolder myViewHolder;
    private List<UrlDetails> seriesList;

    List<CategoryPhotoDetails> photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_album_photos);
        ButterKnife.bind(this);

        Intent i = getIntent();
        final String id = i.getStringExtra("album_id");

        recyclerView.setHasFixedSize(true);
        seriesList = new ArrayList<>();
        myViewHolder = new AllPhotoRecyclerViewHolder(seriesList,getApplicationContext(),"CATEGORY_PHOTO");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myViewHolder);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myViewHolder.clear();
                progressBar.setVisibility(ProgressBar.VISIBLE);
                prepareForData(id);
                myViewHolder.addAll(seriesList);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        prepareForData(id);

    }

    private void prepareForData(final String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/services/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        RetroApi retroApi = retrofit.create(RetroApi.class);

        Call<CategoryPhoto> call = retroApi.getCategotyPhotos(id);


    }
}
