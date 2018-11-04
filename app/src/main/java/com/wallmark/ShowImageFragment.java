package com.wallmark;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowImageFragment extends Fragment {

    ProgressBar progressBar;
    FloatingActionButton fab;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ImageRequest imageRequest;
    ImageLoader imageLoader;
    String downloadUrl;
    NetworkImageView networkImageView;
    public ShowImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view  = inflater.inflate(R.layout.show_image_fragment, container, false);
        networkImageView = view.findViewById(R.id.showImageView);
        fab = view.findViewById(R.id.download_button);
        progressBar = view.findViewById(R.id.progress_horizontal);
        assert getArguments() != null;
        final String url = getArguments().getString("url");
        final String id = getArguments().getString("id");
        final String name = getArguments().getString("name");
        /*Glide.with(getActivity())
                .load(url)
                .into(imageView);*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setScaleX(0);
                fab.setScaleY(0);
                fab.animate().scaleX(1).scaleY(1).start();
                //downloadImage(id,name);
            }
        });
        requestQueue = Volley.newRequestQueue(getActivity());
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url,bitmap);
            }
        });
        networkImageView.setImageUrl(url,imageLoader);
        return view;
    }

    void downloadImage(String id, final String name){
        requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest = new StringRequest(
                Request.Method.GET,
                MyUtil.PHOTO_GETSIZE_URL + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONObject("sizes").getJSONArray("size");
                            JSONObject obj = array.getJSONObject(array.length()-1);
                            downloadUrl = obj.getString("source");
                            imageRequest = new ImageRequest(
                                    downloadUrl,
                                    new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap response) {
                                            try {
                                                Log.i("download1", String.valueOf(response));
                                                FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory()+"/Wall Mark/"+name+".jpg");
                                                response.compress(Bitmap.CompressFormat.JPEG,100,fos);
                                                fos.flush();
                                                Toast.makeText(getActivity(), "Download Completed!", Toast.LENGTH_SHORT).show();
                                                response.recycle();
                                            }catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    },
                                    0,
                                    0,
                                    ImageView.ScaleType.FIT_XY,
                                    Bitmap.Config.RGB_565,
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getActivity(), "Download Failed Please try again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            );
                            requestQueue.add(imageRequest);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Something Wrong Please try again!"+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(stringRequest);
    }
}
