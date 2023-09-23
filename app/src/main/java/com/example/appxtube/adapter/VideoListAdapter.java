package com.example.appxtube.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxtube.R;
import com.example.appxtube.adapter.viewholder.VideoListViewHolder;
import com.example.appxtube.entity.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListViewHolder> {

    List<Item> itemslist;

    public VideoListAdapter(List<Item> itemslist) {
        this.itemslist = itemslist;
    }

    @NonNull
    @Override
    public VideoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list, parent, false);
        return new VideoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListViewHolder holder, int position) {

        Item item = itemslist.get(position);

        Picasso.get().load(item.getSnippet().getThumbnails().getMedium().url).into(holder.tv_thumbnails);

        holder.tv_video_title.setText(item.getSnippet().getTitle());
        holder.tv_video_channel.setText(item.getSnippet().getChannelTitle());

        holder.item = item;
    }

    @Override
    public int getItemCount() {
        return itemslist.size();
    }
}
