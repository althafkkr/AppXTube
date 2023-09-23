package com.example.appxtube.adapter.viewholder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.appxtube.R;
import com.example.appxtube.entity.Item;
import com.example.appxtube.event.EventVideoOptions;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListViewHolder extends RecyclerView.ViewHolder {

    public ImageView tv_thumbnails;
    public TextView tv_video_title;
    public TextView tv_video_channel;
    public TextView tv_video_views;
    public TextView tv_video_age;
    public ImageButton video_options;

    public RelativeLayout rl_video;
    
    public Item item;


    public VideoListViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_thumbnails = itemView.findViewById(R.id.thumbnails);
        tv_video_title = itemView.findViewById(R.id.video_title);
        tv_video_channel = itemView.findViewById(R.id.video_channel_name);
        tv_video_views = itemView.findViewById(R.id.video_views);
        tv_video_age = itemView.findViewById(R.id.video_age);
        video_options = itemView.findViewById(R.id.video_option);
        rl_video =itemView.findViewById(R.id.rl_video);

        rl_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventVideoOptions(item,"play"));
            }
        });

        video_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVideoOptionMenu();
            }
        });


    }

    private void openVideoOptionMenu() {
        EventBus.getDefault().post(new EventVideoOptions(item,"option"));
    }

}
