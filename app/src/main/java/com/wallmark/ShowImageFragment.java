package com.wallmark;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowImageFragment extends Fragment {

    ProgressBar progressBar;
    FloatingActionButton fab;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ImageRequest imageRequest;
    String downloadUrl;
    ImageView imageView;
    public ShowImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view  = inflater.inflate(R.layout.show_image_fragment, container, false);
        fab = view.findViewById(R.id.download_button);
        assert getArguments() != null;
        final String url = getArguments().getString("url");
        final String id = getArguments().getString("id");
        final String name = getArguments().getString("name");
        imageView = view.findViewById(R.id.showImageView);
        Glide.with(getActivity())
                .load(url)
                .into(imageView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setScaleX(0);
                fab.setScaleY(0);
                fab.animate().scaleX(1).scaleY(1).start();
                //downloadImage(id,name);
            }
        });
        return view;
    }

    void downloadImage(String id, final String name){
        /*requestQueue = Volley.newRequestQueue(getActivity());
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
        requestQueue.add(stringRequest);*/
    }
}
