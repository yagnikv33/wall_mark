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


/**
 * A simple {@link Fragment} subclass.
 */
public class PopularPhotoFragment extends Fragment {


    private AllPhotoRecyclerViewHolder myViewHolder;
    private List<UrlDetails> seriesList;
    SwipeRefreshLayout swipeRefreshLayout;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    public PopularPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular_photo, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.popular_photo_recyclerView);


        recyclerView.setHasFixedSize(true);
        seriesList = new ArrayList<>();
        myViewHolder = new AllPhotoRecyclerViewHolder(seriesList,getActivity(),MyUtil.POPULAR_PHOTO_LIST_URL);

        swipeRefreshLayout = view.findViewById(R.id.popularPhotoSwipe);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
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
        requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest = new StringRequest(
                Request.Method.GET,
                MyUtil.POPULAR_PHOTO_LIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj;
                        try {
                            obj = new JSONObject(response);
                            JSONArray array = obj.getJSONObject("photos").getJSONArray("photo");

                            for(int i=0; i<=array.length(); i++){
                                String farm = array.getJSONObject(i).getString("farm");
                                String server = array.getJSONObject(i).getString("server");
                                String id = array.getJSONObject(i).getString("id");
                                String secret = array.getJSONObject(i).getString("secret");
                                String name = array.getJSONObject(i).getString("title");
                                String url = "http://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+".jpg";
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
