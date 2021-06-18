package com.example.fitnice;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnice.api.model.ExerciseContent;
import com.example.fitnice.databinding.ActivityDoRoutine2Binding;

import java.util.ArrayList;

public class DoRoutine2 extends AppCompatActivity {

    ActivityDoRoutine2Binding binding;

    int actual;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDoRoutine2Binding.inflate(getLayoutInflater());

        if (!getResources().getBoolean(R.bool.tablet_player_land) ) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        Bitmap src = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.dudelifting);
        Bitmap cropped = Bitmap.createScaledBitmap(SquareCropper.cropToSquare(src), 285, 285, false);
        binding.exerciseImageView.setImageBitmap(cropped);

        player = Player.getPlayer((ArrayList<ExerciseContent>) getIntent().getSerializableExtra("exList"));
        player.setSeekBar(binding.seekBar);

        setContentView(binding.getRoot());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!getResources().getBoolean(R.bool.tablet_player_land) ) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshEx();

        player.seekBar = binding.seekBar;

        if (!player.isPlaying)
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_play_arrow_24));
        else
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_pause_24));

        binding.NextExBtn.setOnClickListener(v -> nextEx());
        binding.PlayExBtn.setOnClickListener(v -> playPause());
        binding.prevExBtn.setOnClickListener(v -> prevEx());
        binding.closeRoutine2.setOnClickListener(v -> finish());
        binding.changeDoType.setOnClickListener(v -> showList());

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (player.time >= player.MAXPROGRESS) {
                    nextEx();
                }
                if (player.actualExercise != actual) {
                    actual = player.actualExercise;
                    refreshEx();
                }
                if (player.time%5==0)
                    binding.currentTimeText.setText(minSec(player.time/5));
                if (player.cancel)
                    finish();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.setTime(seekBar.getProgress());
            }
        });
    }
    private String minSec(Integer time) {
        return String.format("%d:%02d",time/60,time%60);
    }

    private void showList() {
        Intent intent = new Intent(this, DoRoutineListed.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
        refreshEx();
    }

    private void nextEx() {
        if (player.nextEx()) {
            refreshEx();
        } else {
            finish();
        }
    }

    private void refreshEx() {
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
        Player.destroy();
        overridePendingTransition(0,R.anim.slide_down);
    }
}