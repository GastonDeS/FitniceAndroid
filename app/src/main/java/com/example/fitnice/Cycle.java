package com.example.fitnice;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class Cycle {
    private String cycleName;
    private ArrayList<Exercise> exerciseList;

    public Cycle(String cycleName, ArrayList<Exercise> exerciseList) {
        this.cycleName = cycleName;
        this.exerciseList = exerciseList;
    }

    public String getCycleName() {
        return cycleName;
    }

    public void setCycleName(String cycleName) {
        this.cycleName = cycleName;
    }

    public ArrayList<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(ArrayList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }
}
