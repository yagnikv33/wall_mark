package com.wallmark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

public class ShowImageAcitivity extends AppCompatActivity {

    List<UrlDetails> list;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ShowImageViewPagerAdapter adapter;
    String name,frame,pos;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image_acitivity);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        frame = intent.getStringExtra("frame");
        pos = intent.getStringExtra("pos");

        list = new ArrayList<>();
        viewPager = findViewById(R.id.showImageViewPager);
        adapter = new ShowImageViewPagerAdapter(getSupportFragmentManager(),list);
        viewPager.setAdapter(adapter);
        prepareForData();
    }

    private void prepareForData() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest = new StringRequest(
                Request.Method.GET,
                frame,
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
                                list.add(new UrlDetails(url,id,name));
                                adapter.notifyDataSetChanged();
                                viewPager.setCurrentItem(Integer.parseInt(pos));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Flickr", String.valueOf(error));
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

}
