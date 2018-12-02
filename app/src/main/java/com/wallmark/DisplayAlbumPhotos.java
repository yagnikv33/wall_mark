package com.wallmark;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
        final String album_id = i.getStringExtra("album_id");

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
                prepareForData(album_id);
                myViewHolder.addAll(seriesList);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        prepareForData(album_id);

    }

    private void prepareForData(final String album_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/services/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        RetroApi retroApi = retrofit.create(RetroApi.class);

        Call<CategoryPhoto> call = retroApi.getCategotyPhotos(album_id);

        call.enqueue(new Callback<CategoryPhoto>() {
            @Override
            public void onResponse(@NonNull Call<CategoryPhoto> call, @NonNull Response<CategoryPhoto> response) {
                assert response.body() != null;
                photo = response.body().getCategoryPhotoDetails().getPhoto();
                for (CategoryPhotoDetails list : photo) {
                    int farm = list.getFarm();
                    String server = list.getServer();
                    String id = list.getId();
                    String secret = list.getSecret();
                    String name = list.getTitle();
                    String url = "http://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+"_b.jpg";
                    seriesList.add(new UrlDetails(url,album_id,name));
                    myViewHolder.notifyDataSetChanged();
                    progressBar.setVisibility(ProgressBar.GONE);
                }
                swipeRefreshLayout.setEnabled(true);
            }

            @Override
            public void onFailure(@NonNull Call<CategoryPhoto> call, @NonNull Throwable t) {

            }
        });
    }
}
