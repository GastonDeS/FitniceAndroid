package com.example.fitnice;

import com.example.fitnice.api.model.ExerciseContent;

import java.io.Serializable;
import java.util.ArrayList;

public class Player {

    private static Player instance = null;

    Integer actualExercise = 0;
    Integer time =0 ;
    Integer MAXPROGRESS = 1000;
    Boolean isPlaying = true;
    ArrayList<ExerciseContent> playerList;
    ArrayList<ExerciseContent> exList = new ArrayList<>();

    public static Player getPlayer(ArrayList<ExerciseContent> playerList){
        if (instance==null) {
            instance = new Player(playerList);
        }
        return instance;
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

    private int getStepTime() {
        return Math.round(MAXPROGRESS * 0.2f /  (float) exList.get(actualExercise).getDuration());
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean playPause() {
        if (isPlaying) {
            isPlaying = false;
//            timer.cancel();
            return true;
        } else {
            isPlaying = true;
//            timer = new Timer();
//            timer.scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    time();
//                }
//            },0, 200);
            return false;
        }
    }

    public void prevEx() {
        time = 0;
        if (actualExercise > 0) {
            actualExercise--;
            playerList.add(0,exList.get(actualExercise));
//            exAdapter.notifyDataSetChanged();
        }
    }

    public int time() {
        time+=getStepTime();
        return time;
    }

    public boolean nextEx() {
        if (exList.size() -1 > actualExercise) {
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
