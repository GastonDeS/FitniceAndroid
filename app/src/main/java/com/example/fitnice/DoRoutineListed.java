package com.example.fitnice;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fitnice.api.model.ExerciseContent;
import com.example.fitnice.databinding.ActivityDoRoutineListedBinding;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DoRoutineListed extends AppCompatActivity {

    ActivityDoRoutineListedBinding binding;

//    int actualExercise = 0;
//    int time =0 ;
//    boolean isPlaying = true;
//    boolean showList = true;
    private final int MAXPROGRESS = 1000;
    Player player;
//    Timer timer;
//    ArrayList<ExerciseContent> playerList;
//    ArrayList<ExerciseContent> exList = new ArrayList<>();
    DoRoutineAdapter exAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Toast.makeText(getApplication(),"DUMMY",Toast.LENGTH_LONG).show();

        binding = ActivityDoRoutineListedBinding.inflate(getLayoutInflater());

        player = Player.getPlayer(new ArrayList<>());
        player.setSeekBar(binding.seekBar);
//        playerList = (ArrayList<ExerciseContent>) getIntent().getExtras().getSerializable("exList");
//        exList.addAll(playerList);

//        timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                player();
//            }
//        },0, 200);


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

        binding.seekBar.setMax(MAXPROGRESS);


        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (player.time >= MAXPROGRESS)
                    nextEx();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.time = seekBar.getProgress();
//                Toast.makeText(getApplication(),String.valueOf(time),Toast.LENGTH_LONG).show();
            }
        });

        exAdapter = new DoRoutineAdapter(player.playerList,this);
        binding.recyclerView.setAdapter(exAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setContentView(binding.getRoot());
    }



    private void hideList() {
//        timer.cancel();
//        player.playPause();
        finish();
    }

//    private int getStepTime() {
//        return Math.round(MAXPROGRESS * 0.2f /  (float) exList.get(actualExercise).getDuration());
//    }

    private void playPause() {
        if (player.playPause()/*isPlaying*/) {
//            isPlaying = false;
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_play_arrow_24));
//            timer.cancel();
        } else {
//            isPlaying = true;
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_pause_24));
//            timer = new Timer();
//            timer.scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    player();
//                }
//            },0, 200);
        }
    }

    private void prevEx() {
//        time = 0;
//        if (actualExercise > 0) {
//            actualExercise--;
//            playerList.add(0,exList.get(actualExercise));
//        }
        player.prevEx();
        exAdapter.notifyDataSetChanged();
        refreshEx();

    }

    private void player() {
        player.time();
        binding.seekBar.setProgress(player.time);
    }

    private void nextEx() {
        if (player.nextEx()/*exList.size() -1 > actualExercise*/) {
//            time = 0;
//            actualExercise++;
            refreshEx();
//            playerList.remove(0);
            exAdapter.notifyDataSetChanged();
        } else {
            player.cancel = true;
            finish();
        }
    }

    private void refreshEx() {
        binding.seekBar.setProgress(player.time);
//        Toast.makeText(getApplication(),player.actualExercise +"  "+player.exList.size(),Toast.LENGTH_LONG).show();
        binding.reps.setText(player.exList.get(player.actualExercise).getRepetitions().toString());
        binding.playerExName.setText(player.exList.get(player.actualExercise).getExercise().getName());
        binding.exDescription.setText(player.exList.get(player.actualExercise).getExercise().getDetail());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.slide_down);
    }
}