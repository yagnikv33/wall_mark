package com.wallmark;


import android.animation.ObjectAnimator;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowImageFragment extends Fragment {

    //FloatingActionButton fab;
    @BindView(R.id.showImageView)
    ImageView imageView;

    @BindView(R.id.download)
    TextView download;

    @BindView(R.id.image_title)
    TextView title;

    @BindView(R.id.titleLayout)
    LinearLayout titleLayout;

    @BindView(R.id.buttonLayout)
    LinearLayout buttonLayout;

    @BindView(R.id.showimage_fragment_progressbar)
    ProgressBar progressBar;

    List<Size> photo;
    private DownloadManager downloadmanager;
    int flag;

    public ShowImageFragment() {
        // Required empty
        // public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view  = inflater.inflate(R.layout.show_image_fragment, container, false);
        ButterKnife.bind(this,view);

        downloadmanager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

        assert getArguments() != null;
        final String url = getArguments().getString("url");
        final String id = getArguments().getString("id");
        final String name = getArguments().getString("name");

        Glide.with(getActivity())
                .load(url)
                .into(imageView);
        title.setText(name);
        progressBar.setVisibility(ProgressBar.GONE);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download.setScaleX(0);
                download.setScaleY(0);
                download.animate().scaleX(1).scaleY(1).start();
                downloadImage(id,name);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1){
                    titleLayout.setVisibility(View.VISIBLE);
                    buttonLayout.setVisibility(View.VISIBLE);
                    flag = 0;
                }
                else{
                    titleLayout.setVisibility(View.GONE);
                    buttonLayout.setVisibility(View.GONE);
                    flag = 1;
                }
                imageView.setClickable(true);
            }
        });
        return view;
    }

    void downloadImage(String id, final String name){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/services/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        RetroApi retroApi = retrofit.create(RetroApi.class);

        Call<PhotoSize> call = retroApi.getPhotoSize(id);

        call.enqueue(new Callback<PhotoSize>() {
            @Override
            public void onResponse(Call<PhotoSize> call, Response<PhotoSize> response) {
                photo = response.body().getSizes().getSize();
                Uri download_uri = Uri.parse(photo.get(photo.size()-1).getSource());
                DownloadManager.Request request = new DownloadManager.Request(download_uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setTitle(name);
                request.setDescription(name +".jpg");
                request.setVisibleInDownloadsUi(true);
                request.setDestinationInExternalPublicDir("Wall Mark",  name + ".jpg");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadmanager.enqueue(request);
            }

            @Override
            public void onFailure(Call<PhotoSize> call, Throwable t) {

            }
        });

    }

}
