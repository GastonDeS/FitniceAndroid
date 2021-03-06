package com.example.fitnice;

import android.widget.SeekBar;

import com.example.fitnice.api.model.ExerciseContent;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Player {

    private static Player instance = null;

    Integer actualExercise = 0;
    Integer time =0 ;
    Integer MAXPROGRESS = 500;
    Boolean isPlaying = false;
    Boolean cancel = false;
    Timer timer = new Timer();
    SeekBar seekBar;
    ArrayList<ExerciseContent> playerList;
    ArrayList<ExerciseContent> exList = new ArrayList<>();

    public static Player getPlayer(ArrayList<ExerciseContent> playerList){
        if (instance==null) {
            instance = new Player(playerList);
        }
        return instance;
    }

    public void setSeekBar(SeekBar seekBar ) {
        this.seekBar = seekBar;
        this.MAXPROGRESS = exList.get(actualExercise).getDuration()*5;
        this.seekBar.setMax(MAXPROGRESS);
        if (time == 0) {
            playPause();
            playerList.remove(0);
        }
    }

    public static void destroy() {
        instance = null;
    }

    private Player(ArrayList<ExerciseContent> playerList) {
        this.playerList = playerList;
        exList.addAll(playerList);
//        if (instance==null) {
//            instance = this;
//            this.playerList = playerList;
//            exList.addAll(playerList);
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                time();
//            }
//        },0, 200);
//        }
    }

//    public int getStepTime() {
//        return Math.round((MAXPROGRESS * 0.2f) /  (float) exList.get(actualExercise).getDuration());
//    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean playPause() {
        if (isPlaying) {
            isPlaying = false;
            timer.cancel();
            return true;
        } else {
            isPlaying = true;
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    time();
                }
            },0, 200);
            return false;
        }
    }

    public void prevEx() {
        time = 0;
        if (actualExercise > 0) {
            actualExercise--;
            playerList.add(0,exList.get(actualExercise+1));
//            exAdapter.notifyDataSetChanged();
        }
        this.MAXPROGRESS = exList.get(actualExercise).getDuration()*5;
        seekBar.setMax(MAXPROGRESS);
    }

    public void time() {
        time++;
        seekBar.setProgress(time);
    }

    public boolean nextEx() {

        if (exList.size() -1 > actualExercise) {
            this.MAXPROGRESS = exList.get(actualExercise+1).getDuration()*5;
            seekBar.setMax(MAXPROGRESS);
            time = 0;
            actualExercise++;
//            refreshEx();
            playerList.remove(0);
            return true;
//            exAdapter.notifyDataSetChanged();
        } else {
//            finish();
            return false;
        }
    }
}
