package com.example.fitnice;

import android.os.Bundle;
import android.os.Handler;
import android.telecom.Call;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fitnice.api.model.ExerciseContent;
import com.example.fitnice.databinding.ActivityDoRoutine2Binding;
import com.example.fitnice.repository.Status;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class DoRoutine2 extends AppCompatActivity {

    ActivityDoRoutine2Binding binding;

    int actualExercise = 0;
    int time =0 ;
    boolean isPlaying = true;
    boolean showList = true;
    ArrayList<ExerciseContent> playerList;
    ArrayList<ExerciseContent> exList = new ArrayList<>();
    DoRoutineAdapter exAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDoRoutine2Binding.inflate(getLayoutInflater());


        playerList = (ArrayList<ExerciseContent>) getIntent().getSerializableExtra("exList");
        exList.addAll(playerList);

        refreshEx();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                player();
            }
        },0, 1000);

        binding.NextExBtn.setOnClickListener(v -> nextEx());
        binding.PlayExBtn.setOnClickListener(v -> playPause());
        binding.prevExBtn.setOnClickListener(v -> prevEx());
        binding.closeRoutine2.setOnClickListener(v -> finish());
        binding.changeDoType.setOnClickListener(v -> showHideList());


        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (time >= 100)
                    nextEx();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                time = seekBar.getProgress();
//                Toast.makeText(getApplication(),String.valueOf(time),Toast.LENGTH_LONG).show();
            }
        });

        exAdapter = new DoRoutineAdapter(playerList,this);
        binding.recyclerView.setAdapter(exAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setContentView(binding.getRoot());
    }

    private void showHideList() {
        if (showList) {
            binding.exDescription.setVisibility(View.INVISIBLE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.imageView4.setVisibility(View.INVISIBLE);
            showList = false;
        } else {
            binding.exDescription.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
            binding.imageView4.setVisibility(View.VISIBLE);
            showList = true;
        }
    }


    private void playPause() {
        if (isPlaying) {
            isPlaying = false;
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_play_arrow_24));
        } else {
            isPlaying = true;
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_pause_24));
        }
    }

    private void prevEx() {
        time = 0;
        if (actualExercise > 0) {
            actualExercise--;
            playerList.add(0,exList.get(actualExercise));
            exAdapter.notifyDataSetChanged();
        }
        refreshEx();

    }

    private void player() {
        if (isPlaying) {
            time++;
            binding.seekBar.setProgress(time);
        }
    }

    private void nextEx() {
        if (exList.size() -1 > actualExercise) {
            time = 0;
            actualExercise++;
            refreshEx();
            playerList.remove(0);
            exAdapter.notifyDataSetChanged();
        } else {
            finish();
        }
    }

    private void refreshEx() {
        binding.seekBar.setProgress(time);
        binding.reps.setText(exList.get(actualExercise).getRepetitions().toString());
        binding.playerExName.setText(exList.get(actualExercise).getExercise().getName());
        binding.actExSec.setText(exList.get(actualExercise).getDuration().toString());
        binding.exDescription.setText(exList.get(actualExercise).getExercise().getDetail());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.slide_down);
    }
}