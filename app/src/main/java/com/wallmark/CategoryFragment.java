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
public class CategoryFragment extends Fragment {

    private CategoryRecyclerViewHolder myViewHolder;
    private List<UrlDetails> seriesList;
    Context context;
    RequestQueue requestQueue;
    StringRequest stringRequest;
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
        requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest = new StringRequest(
                Request.Method.GET,
                MyUtil.PHOTOSET_LIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj;
                        try {
                            obj = new JSONObject(response);

                            JSONArray array = obj.getJSONObject("photosets").getJSONArray("photoset");

                            for(int i=0; i<=array.length(); i++){
                                String id = array.getJSONObject(i).getString("id");
                                String farm = array.getJSONObject(i).getString("farm");
                                String server = array.getJSONObject(i).getString("server");
                                String primary = array.getJSONObject(i).getString("primary");
                                String secret = array.getJSONObject(i).getString("secret");
                                String name = array.getJSONObject(i).getJSONObject("title").getString("_content");
                                String url = "http://farm"+farm+".staticflickr.com/"+server+"/"+primary+"_"+secret+".jpg";
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
