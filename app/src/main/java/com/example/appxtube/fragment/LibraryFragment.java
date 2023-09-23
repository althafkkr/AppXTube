package com.example.appxtube.fragment;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appxtube.listener.OnBackPressedListener;
import com.example.appxtube.R;
import com.example.appxtube.adapter.HistoryListAdapter;
import com.example.appxtube.comparator.DateComparator;
import com.example.appxtube.entity.Item;
import com.example.appxtube.entity.VideoEntity;
import com.example.appxtube.event.EventGoHome;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LibraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LibraryFragment extends Fragment implements OnBackPressedListener {

    RecyclerView rv_history;


    public LibraryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        rv_history = view.findViewById(R.id.rv_history);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_history.setLayoutManager(layoutManager);

        getVideoList();

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
        List<Item> itemList = videoEntity.getItems();
        Collections.sort(itemList,new DateComparator());
        HistoryListAdapter videoListAdapter = new HistoryListAdapter(itemList);
        rv_history.setAdapter(videoListAdapter);
    }


    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new EventGoHome());
    }
}