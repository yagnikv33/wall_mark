package com.wallmark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayAlbumPhotos extends AppCompatActivity {

    private AllPhotoRecyclerViewHolder myViewHolder;
    private List<UrlDetails> seriesList;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_album_photos);
        Intent i = getIntent();
        final String id = i.getStringExtra("album_id");
        RecyclerView recyclerView = findViewById(R.id.album_recyclerView);
        recyclerView.setHasFixedSize(true);
        seriesList = new ArrayList<>();
        myViewHolder = new AllPhotoRecyclerViewHolder(seriesList,getApplicationContext(),MyUtil.PHOTOSET_PHOTO_URL + id);

        swipeRefreshLayout = findViewById(R.id.albumPhotoSwipe);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myViewHolder);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myViewHolder.clear();
                prepareForData(id);
                myViewHolder.addAll(seriesList);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        prepareForData(id);

    }

    private void prepareForData(String id) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest = new StringRequest(
                Request.Method.GET,
                MyUtil.PHOTOSET_PHOTO_URL + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj;
                        try {
                            obj = new JSONObject(response);
                            JSONArray array = obj.getJSONObject("photoset").getJSONArray("photo");

                            for(int i=0; i<=array.length(); i++){
                                String farm = array.getJSONObject(i).getString("farm");
                                String server = array.getJSONObject(i).getString("server");
                                String id = array.getJSONObject(i).getString("id");
                                String secret = array.getJSONObject(i).getString("secret");
                                String name = array.getJSONObject(i).getString("title");
                                String url = "http://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+"_b.jpg";
                                seriesList.add(new UrlDetails(url,id,name));
                                myViewHolder.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(stringRequest);
    }
}
