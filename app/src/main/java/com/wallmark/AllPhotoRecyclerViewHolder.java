package com.wallmark;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class  AllPhotoRecyclerViewHolder extends RecyclerView.Adapter<AllPhotoRecyclerViewHolder.ViewHolder> {
    private List<UrlDetails> myLists;
    public Context context;
    private String frame;

    AllPhotoRecyclerViewHolder(List<UrlDetails> myLists, Context context, String frame) {
        this.myLists = myLists;
        this.context = context;
        this.frame = frame;
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.display_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        final String name = myLists.get(i).getName();
        Glide.with(context)
                .load(myLists.get(i).getUrl())
                .into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShowImageAcitivity.class);
                intent.putExtra("name",name);
                intent.putExtra("id",myLists.get(i).getId());
                intent.putExtra("frame",frame);
                intent.putExtra("pos",String.valueOf(i));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rImage)
        ImageView imageView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
