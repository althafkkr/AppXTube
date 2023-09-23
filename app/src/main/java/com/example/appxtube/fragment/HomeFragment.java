package com.example.appxtube.fragment;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appxtube.R;
import com.example.appxtube.adapter.VideoListAdapter;
import com.example.appxtube.entity.Item;
import com.example.appxtube.entity.VideoEntity;
import com.example.appxtube.service.ApiClient;
import com.example.appxtube.service.RetrofitClientInstance;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView rv_video_list;

    private ApiClient apiClient;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        apiClient = RetrofitClientInstance.getInstance().create(ApiClient.class);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);

        shimmerFrameLayout.startShimmerAnimation();
        rv_video_list = view.findViewById(R.id.rv_video_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()); // You can choose a different layout manager.
        rv_video_list.setLayoutManager(layoutManager);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getVideoList();
            }
        },2000);


        return view;
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
        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
        rv_video_list.setVisibility(View.VISIBLE);
        VideoListAdapter videoListAdapter = new VideoListAdapter(videoEntity.getItems());
        rv_video_list.setAdapter(videoListAdapter);
    }

    public void createVideoOptionDialog(Item item) {
        Toast.makeText(getContext(), ""+item.getKind(), Toast.LENGTH_SHORT).show();
    }
}