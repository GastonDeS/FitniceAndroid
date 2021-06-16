package com.example.fitnice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnice.api.model.ExerciseContent;
import com.example.fitnice.databinding.ActivityDoRoutine2Binding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DoRoutine2 extends AppCompatActivity {

    ActivityDoRoutine2Binding binding;

//    int time =0 ;
    private final int MAXPROGRESS = 1000;
    Timer timer;
    int actual;
//    ArrayList<ExerciseContent> playerList;
//    ArrayList<ExerciseContent> exList = new ArrayList<>();
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDoRoutine2Binding.inflate(getLayoutInflater());


//        playerList = (ArrayList<ExerciseContent>) getIntent().getSerializableExtra("exList");
//        exList.addAll(playerList);

        player = Player.getPlayer((ArrayList<ExerciseContent>) getIntent().getSerializableExtra("exList"));

        refreshEx();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                player();
            }
        },0, 200);


        binding.NextExBtn.setOnClickListener(v -> nextEx());
        binding.PlayExBtn.setOnClickListener(v -> playPause());
        binding.prevExBtn.setOnClickListener(v -> prevEx());
        binding.closeRoutine2.setOnClickListener(v -> finish());
        binding.changeDoType.setOnClickListener(v -> showList());

        binding.seekBar.setMax(MAXPROGRESS);


        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (player.time >= MAXPROGRESS) {
                    nextEx();
                }
                if (player.actualExercise != actual) {
                    actual = player.actualExercise;
                    refreshEx();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.setTime(seekBar.getProgress());
//                Toast.makeText(getApplication(),String.valueOf(time),Toast.LENGTH_LONG).show();
            }
        });

//        exAdapter = new DoRoutineAdapter(playerList,this);
//        binding.recyclerView.setAdapter(exAdapter);
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setContentView(binding.getRoot());
    }

    private void showList() {
//        NavController nav = Navigation.findNavController();
        Intent intent = new Intent(this, DoRoutineListed.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Bundle args = new Bundle();
//        Toast.makeText(getApplication(),String.valueOf(time),Toast.LENGTH_LONG).show();
//        args.putInt("id", getIntent().getIntExtra("id", 0));
//        args.putSerializable("exList", (Serializable) playerList);
//        args.putSerializable("player", (Serializable) player);
//        intent.putExtras(args);
        startActivity(intent);


//        nav.navigate(R.id.navigation2,args);
    }

//        private void showHideList() {
//            if (showList) {
//                binding.exDescription.setVisibility(View.INVISIBLE);
//                binding.recyclerView.setVisibility(View.VISIBLE);
//                binding.imageView4.setVisibility(View.INVISIBLE);
//                showList = false;
//            } else {
//                binding.exDescription.setVisibility(View.VISIBLE);
//                binding.recyclerView.setVisibility(View.GONE);
//                binding.imageView4.setVisibility(View.VISIBLE);
//                showList = true;
//            }
//        }

//    private int getStepTime() {
//        return Math.round(MAXPROGRESS * 0.2f /  (float) exList.get(actualExercise).getDuration());
//    }

    private void playPause() {
        if (player.playPause()/*isPlaying*/) {
//            isPlaying = false;
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_play_arrow_24));
            timer.cancel();
        } else {
//            isPlaying = true;
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_pause_24));
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    player();
                }
            },0, 200);
        }
    }

    private void prevEx() {
//        time = 0;
//        if (actualExercise > 0) {
//            actualExercise--;
//            playerList.add(0,exList.get(actualExercise));
////            exAdapter.notifyDataSetChanged();
//        }
        player.prevEx();
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
//            exAdapter.notifyDataSetChanged();
        } else {
            finish();
        }
    }

    private void refreshEx() {
        binding.seekBar.setProgress(player.time);
        binding.reps.setText(player.exList.get(player.actualExercise).getRepetitions().toString());
        binding.playerExName.setText(player.exList.get(player.actualExercise).getExercise().getName());
        binding.actExSec.setText(player.exList.get(player.actualExercise).getDuration().toString());
        binding.exDescription.setText(player.exList.get(player.actualExercise).getExercise().getDetail());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.slide_down);
    }
}