package com.example.fitnice;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnice.api.model.Executions;
import com.example.fitnice.api.model.ExerciseContent;
import com.example.fitnice.databinding.ActivityDoRoutine2Binding;
import com.example.fitnice.repository.Status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DoRoutine2 extends AppCompatActivity {

    ActivityDoRoutine2Binding binding;

//    int time =0 ;
//    private final int MAXPROGRESS = 500;
//    Timer timer;
    int actual;
//    ArrayList<ExerciseContent> playerList;
//    ArrayList<ExerciseContent> exList = new ArrayList<>();
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

//        playerList = (ArrayList<ExerciseContent>) getIntent().getSerializableExtra("exList");
//        exList.addAll(playerList);

        player = Player.getPlayer((ArrayList<ExerciseContent>) getIntent().getSerializableExtra("exList"));
        player.setSeekBar(binding.seekBar);

//        exAdapter = new DoRoutineAdapter(playerList,this);
//        binding.recyclerView.setAdapter(exAdapter);
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Executions executions = new Executions();
        executions.setDuration(10);
        executions.setWasModified(true);



        App app = (App) getApplication();
        app.getExecutionsRepository().postExecution(executions).observe(this,r ->{
            if (r.getStatus() == Status.SUCCESS)
                Toast.makeText(getApplication(),"POST",Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(getApplication(),"FAIL",Toast.LENGTH_LONG).show();
            }
        });

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

//        timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                player();
//            }
//        },0, 200);

        player.seekBar = binding.seekBar;
//        playPause();
//        playPause();

        if (!player.isPlaying)
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_play_arrow_24));
        else
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_pause_24));

        binding.NextExBtn.setOnClickListener(v -> nextEx());
        binding.PlayExBtn.setOnClickListener(v -> playPause());
        binding.prevExBtn.setOnClickListener(v -> prevEx());
        binding.closeRoutine2.setOnClickListener(v -> finish());
        binding.changeDoType.setOnClickListener(v -> showList());

//        binding.seekBar.setMax(MAXPROGRESS);


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
//                Toast.makeText(getApplication(),String.valueOf(time),Toast.LENGTH_LONG).show();
            }
        });
    }
    private String minSec(Integer time) {
        return String.format("%d:%02d",time/60,time%60);
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
//        timer.cancel();
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
//            Player.isPlaying = false;
            binding.PlayExBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_play_arrow_24));
//            timer.cancel();
            Toast.makeText(getBaseContext(),"DUMMY1",Toast.LENGTH_LONG).show();
        } else {
//            Player.isPlaying = true;
            Toast.makeText(getBaseContext(),"DUMMY2",Toast.LENGTH_LONG).show();
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
////            exAdapter.notifyDataSetChanged();
//        }
        player.prevEx();
        refreshEx();
    }

//    private void player() {
//        player.time();
//        binding.seekBar.setProgress(player.time);
//    }

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

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
//        Bitmap src = BitmapFactory.decodeResource(context.getResources(), R.drawable.dudelifting);
//        Bitmap cropped = SquareCropper.cropToSquare(src);
//        binding.exerciseImageView.setImageBitmap(cropped);
//
//        return super.onCreateView(name, context, attrs);
//    }

    private void refreshEx() {
//        binding.seekBar.setProgress(player.time);
        Toast.makeText(getBaseContext(),String.format("%d",player.MAXPROGRESS),Toast.LENGTH_LONG).show();
        binding.totalTimeText.setText(minSec(player.exList.get(player.actualExercise).getDuration()));
//        binding.reps.setText(player.exList.get(player.actualExercise).getRepetitions().toString());
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