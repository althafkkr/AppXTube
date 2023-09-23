package com.example.appxtube.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxtube.R;
import com.example.appxtube.adapter.viewholder.HistoryListViewHolder;
import com.example.appxtube.adapter.viewholder.VideoListViewHolder;
import com.example.appxtube.entity.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListViewHolder> {

    List<Item> itemList;

    public HistoryListAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public HistoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.histroy_view, parent, false);
        return new HistoryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListViewHolder holder, int position) {
        Item item = itemList.get(position);

        Picasso.get().load(item.getSnippet().getThumbnails().getMedium().url).into(holder.thumbnails);

        holder.tv_video_title.setText(item.getSnippet().getTitle());
        holder.tv_channel_name.setText(item.getSnippet().getChannelTitle());

        holder.item = item;
    }

    @Override
    public int getItemCount() {
        return Math.min(itemList.size(), 10);
    }
}
