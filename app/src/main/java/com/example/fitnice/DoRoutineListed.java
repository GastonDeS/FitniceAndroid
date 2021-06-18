package com.example.fitnice;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fitnice.api.model.ExerciseContent;
import com.example.fitnice.databinding.ActivityDoRoutineListedBinding;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DoRoutineListed extends AppCompatActivity {

    ActivityDoRoutineListedBinding binding;

    boolean landscape = false;
    int actual = 0;
    Player player;
    DoRoutineAdapter exAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityDoRoutineListedBinding.inflate(getLayoutInflater());

        if (!getResources().getBoolean(R.bool.tablet_player_land) ) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        player = Player.getPlayer(new ArrayList<>());
        player.setSeekBar(binding.seekBar);

        refreshEx();

        if (!player.isPlaying)
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_play_arrow_24));
        else
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_pause_24));
        binding.NextExBtn.setOnClickListener(v -> nextEx());
        binding.PlayExBtn.setOnClickListener(v -> playPause());
        binding.prevExBtn.setOnClickListener(v -> prevEx());
        binding.closeRoutine2.setOnClickListener(v -> {
            player.cancel = true;
            finish();
        });
        binding.changeDoType.setOnClickListener(v -> hideList());

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (player.time >= player.MAXPROGRESS)
                    nextEx();
                if (player.actualExercise != actual) {
                    actual = player.actualExercise;
                    refreshEx();
                }
                if (player.time%5==0)
                    binding.currentTimeText2.setText(minSec(player.time/5));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.time = seekBar.getProgress();
            }
        });

        exAdapter = new DoRoutineAdapter(player.playerList,this);
        binding.recyclerView.setAdapter(exAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setContentView(binding.getRoot());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getBoolean(R.bool.tablet_player_land) ){
            if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                landscape = true;
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                landscape = false;
            }
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private String minSec(Integer time) {
        return String.format("%02d:%02d",time/60,time%60);
    }



    private void hideList() {
        finish();
    }

    private void playPause() {
        if (player.playPause()) {
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_play_arrow_24));
        } else {
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_pause_24));
        }
    }

    private void prevEx() {
        player.prevEx();
        exAdapter.notifyDataSetChanged();
        refreshEx();

    }

    private void player() {
        player.time();
        binding.seekBar.setProgress(player.time);
    }

    private void nextEx() {
        if (player.nextEx()) {
            refreshEx();
            exAdapter.notifyDataSetChanged();
        } else {
            player.cancel = true;
            finish();
        }
    }

    private void refreshEx() {
        binding.seekBar.setProgress(player.time);
        binding.totalTimeText.setText(minSec(player.exList.get(player.actualExercise).getDuration()));
        if (player.exList.get(player.actualExercise).getRepetitions()>1)
            binding.reps.setText(getResources().getString(R.string.cycleReps,player.exList.get(player.actualExercise).getRepetitions().toString()));
        else
            binding.reps.setText("");
        binding.playerExName.setText(player.exList.get(player.actualExercise).getExercise().getName());
        binding.exDescription.setText(player.exList.get(player.actualExercise).getExercise().getDetail());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.slide_down);
    }
}