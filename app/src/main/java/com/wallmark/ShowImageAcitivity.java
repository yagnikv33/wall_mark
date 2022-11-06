package com.wallmark;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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

public class ShowImageAcitivity extends AppCompatActivity {

    @BindView(R.id.showImageViewPager)
    ViewPager viewPager;

    @BindView(R.id.show_image_activity_progressbar)
    ProgressBar progressBar;

    List<UrlDetails> list;
    ShowImageViewPagerAdapter adapter;
    String name, frame, pos, id;

    List<Photo> photo;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.flickr.com/services/")
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
            .build();

    RetroApi retroApi = retrofit.create(RetroApi.class);

    Call<Model> call = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image_acitivity);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(null);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        frame = intent.getStringExtra("frame");
        pos = intent.getStringExtra("pos");
        id = intent.getStringExtra("id");

        list = new ArrayList<>();
        adapter = new ShowImageViewPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        prepareForData();
    }

    private void prepareForData() {
        switch (frame) {
            case "ALL_PHOTO":
                call = retroApi.getSerch();
                getAllPhotos();
                break;

            case "POPULAR_PHOTO":
                call = retroApi.getPopular();
                getAllPhotos();
                break;

            case "CATEGORY_PHOTO":
                Call<CategoryPhoto> cateCall = retroApi.getCategotyPhotos(id);
                cateCall.enqueue(new Callback<CategoryPhoto>() {
                    @Override
                    public void onResponse(@NonNull Call<CategoryPhoto> call, @NonNull Response<CategoryPhoto> response) {
                        assert response.body() != null;
                        List<CategoryPhotoDetails> photo = response.body().getCategoryPhotoDetails().getPhoto();
                        for (CategoryPhotoDetails lists : photo) {
                            int farm = lists.getFarm();
                            String server = lists.getServer();
                            String id = lists.getId();
                            String secret = lists.getSecret();
                            String name = lists.getTitle();
                            String url = "http://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_b.jpg";
                            list.add(new UrlDetails(url, id, name));
                            adapter.notifyDataSetChanged();
                            viewPager.setCurrentItem(Integer.parseInt(pos));
                            progressBar.setVisibility(ProgressBar.GONE);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CategoryPhoto> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "Some thing Wrong! Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    private void getAllPhotos() {
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(@NonNull Call<Model> call, @NonNull retrofit2.Response<Model> response) {
                assert response.body() != null;
                photo = response.body().getPhotos().getPhoto();
                for (Photo lists : photo) {
                    int farm = lists.getFarm();
                    String server = lists.getServer();
                    String id = lists.getId();
                    String secret = lists.getSecret();
                    String name = lists.getTitle();
                    String url = "http://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_b.jpg";
                    list.add(new UrlDetails(url, id, name));
                    adapter.notifyDataSetChanged();
                    viewPager.setCurrentItem(Integer.parseInt(pos));
                    progressBar.setVisibility(ProgressBar.GONE);
                }

            }

            @Override
            public void onFailure(@NonNull Call<Model> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Some thing Wrong! Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}