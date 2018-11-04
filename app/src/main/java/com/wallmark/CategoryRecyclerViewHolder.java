package com.wallmark;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.List;

public class CategoryRecyclerViewHolder extends RecyclerView.Adapter<CategoryRecyclerViewHolder.ViewHolder> {
    private List<UrlDetails> myLists;
    public Context context;
    RequestQueue requestQueue;
    ImageLoader imageLoader;
    CategoryRecyclerViewHolder(List<UrlDetails> myLists, Context context) {
        this.myLists = myLists;
        this.context = context;
    }

    void clear(){
        myLists.clear();
        notifyDataSetChanged();
    }

    void addAll(List<UrlDetails> list){
        myLists.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item_cardview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final String id = myLists.get(i).getId();
        final String name = myLists.get(i).getName();
        requestQueue = Volley.newRequestQueue(context);
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
        viewHolder.imageView.setImageUrl(myLists.get(i).getUrl(),imageLoader);
        viewHolder.textView.setText(name);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,DisplayAlbumPhotos.class);
                i.putExtra("album_id",id);
                i.putExtra("album_name",name);
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView imageView;
        TextView textView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.categoryImage);
            textView = itemView.findViewById(R.id.categoryName);
        }
    }
}
