package com.example.appxtube.fragment;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appxtube.listener.OnBackPressedListener;
import com.example.appxtube.R;
import com.example.appxtube.adapter.VideoListAdapter;
import com.example.appxtube.comparator.DateComparator;
import com.example.appxtube.entity.Item;
import com.example.appxtube.entity.VideoEntity;
import com.example.appxtube.event.EventGoHome;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedFragment extends Fragment implements OnBackPressedListener {


    private Item item;

    RecyclerView  rv_details;

    ImageView imageView;
    TextView iv_video_title,tv_video_history,tv_hitsory_more,tv_channel_name,tv_views,tv_subscribe;

    private boolean isFullTextVisible = false;

    public DetailedFragment() {
        // Required empty public constructor
    }

    public DetailedFragment(Item item) {
        this.item = item;
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detailed, container, false);

        imageView = view.findViewById(R.id.im_thumbnails);
        iv_video_title = view.findViewById(R.id.iv_video_title);
        tv_video_history = view.findViewById(R.id.tv_video_history);
        tv_hitsory_more = view.findViewById(R.id.tv_hitsory_more);
        tv_channel_name = view.findViewById(R.id.tv_channel_name);
        tv_views = view.findViewById(R.id.tv_views);
        tv_subscribe = view.findViewById(R.id.tv_subscribe);
        rv_details = view.findViewById(R.id.rv_details);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_details.setLayoutManager(linearLayoutManager);

        Picasso.get().load(item.getSnippet().getThumbnails().getMedium().url).into(imageView);
        iv_video_title.setText(item.getSnippet().getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_video_history.setText(getTimeAgo(dateToString(item.getSnippet().publishedAt,"yyyy-MM-dd")) +item.getSnippet().getDescription());
        } else {
            tv_video_history.setText(item.getSnippet().getDescription());
        }
        tv_channel_name.setText(item.getSnippet().getChannelTitle());

        getVideoList();


        tv_hitsory_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFullTextVisible) {

                    tv_video_history.setMaxLines(1);
                    tv_video_history.setEllipsize(TextUtils.TruncateAt.END);
                    tv_hitsory_more.setText("Show More");
                } else {
                    tv_video_history.setMaxLines(Integer.MAX_VALUE);
                    tv_video_history.setEllipsize(null);
                    tv_hitsory_more.setVisibility(View.GONE);
                }

                isFullTextVisible = !isFullTextVisible;
            }
        });

        return  view;
    }

    private void getVideoList() {
        AssetManager assetManager = getActivity().getAssets();

        try {
            InputStream inputStream = assetManager.open("home_datas.json");
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            VideoEntity videoEntity = gson.fromJson(reader,VideoEntity.class);
            setVideoListToAdapter(videoEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setVideoListToAdapter(VideoEntity videoEntity) {
        List<Item> itemList = videoEntity.getItems();
        Collections.sort(itemList,new DateComparator());
        VideoListAdapter videoListAdapter = new VideoListAdapter(itemList);
        rv_details.setAdapter(videoListAdapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getTimeAgo(String dateString) {
        LocalDate date = LocalDate.parse(dateString); // Parse the given date string

        LocalDate now = LocalDate.now();
        Period period = Period.between(date, now);

        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();

        if (years > 0) {
            return years + " " + pluralize("year", years) + " ago";
        } else if (months > 0) {
            return months + " " + pluralize("month", months) + " ago";
        } else if (days > 0) {
            return days + " " + pluralize("day", days) + " ago";
        } else {
            return "Today"; // The date is today
        }
    }

    private static String pluralize(String word, int count) {
        return count == 1 ? word : word + "s";
    }

    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }


    @Override
    public void onBackPressed() {

        EventBus.getDefault().post(new EventGoHome());
    }
}