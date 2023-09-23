package com.example.appxtube.adapter.viewholder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxtube.R;
import com.example.appxtube.entity.Item;
import com.example.appxtube.event.EventVideoOptions;

import org.greenrobot.eventbus.EventBus;

public class HistoryListViewHolder extends RecyclerView.ViewHolder {

    public ImageView thumbnails;

    public TextView tv_video_title,tv_channel_name;

    public RelativeLayout rl_history_video;

    public Item item;

    ImageButton video_option;

    public HistoryListViewHolder(@NonNull View itemView) {
        super(itemView);
        thumbnails = itemView.findViewById(R.id.his_thumbnails);
        tv_video_title = itemView.findViewById(R.id.tv_video_title);
        tv_channel_name = itemView.findViewById(R.id.tv_video_channel);
        rl_history_video = itemView.findViewById(R.id.rl_history_video);
        video_option = itemView.findViewById(R.id.video_option);

        rl_history_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventVideoOptions(item,"play"));
            }
        });

        video_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventVideoOptions(item,"option"));
            }
        });

    }
}
