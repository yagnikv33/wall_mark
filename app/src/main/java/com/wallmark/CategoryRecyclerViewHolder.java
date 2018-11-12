package com.wallmark;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryRecyclerViewHolder extends RecyclerView.Adapter<CategoryRecyclerViewHolder.ViewHolder> {
    private List<UrlDetails> myLists;
    public Context context;
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
        Glide.with(context).load(myLists.get(i).getUrl()).into(viewHolder.imageView);
        viewHolder.textView.setText(name);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,DisplayAlbumPhotos.class);
                i.putExtra("album_id",id);
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.categoryImage)
        ImageView imageView;

        @BindView(R.id.categoryName)
        TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
