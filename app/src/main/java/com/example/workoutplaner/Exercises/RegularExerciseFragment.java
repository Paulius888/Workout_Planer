package com.example.workoutplaner.Exercises;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.workoutplaner.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class RegularExerciseFragment extends Fragment {

    private Exercise exercise;
    private ArrayList<ExerciseInput> exerciseInputs;
    AnimationDrawable avd;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RegExerciseAdapter regAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_regular_exercise, container, false);
        swipeRefreshLayout = rootView.findViewById(R.id.swip);
        recyclerView = rootView.findViewById(R.id.rv_regular);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        YouTubePlayerView youTubePlayerView = rootView.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        regAdapter = new RegExerciseAdapter(getActivity());
        recyclerView.setAdapter(regAdapter);
        exercise = (Exercise) getArguments().getSerializable("exercise");
        exerciseInputs = (ArrayList<ExerciseInput>)getArguments().getSerializable("regularExercisesList");
        if (exercise.getVideoID().isEmpty()) {
            ImageView done = rootView.findViewById(R.id.imageView3);
            try {
                youTubePlayerView.setVisibility(View.INVISIBLE);
                done.setVisibility(View.VISIBLE);
                avd = (AnimationDrawable) done.getDrawable();
                avd.start();
                Log.e("OKAY", "OKAY!!");
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
        }
        else {
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoid = exercise.getVideoID();
                    String videoId = videoid;
                    youTubePlayer.cueVideo(videoId, 0);
                }
            });
        }

        for (int i = 0; i < exercise.getSets(); i++){
            exerciseInputs.add(null);
        }
        regAdapter.setItems(exerciseInputs);

        return rootView;
    }
}