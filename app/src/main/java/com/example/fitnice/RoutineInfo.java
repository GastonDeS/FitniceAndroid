package com.example.fitnice;

import java.util.ArrayList;

public class RoutineInfo {

    String name;
    String description;
    String category;
    String duration;
    String difficulty;
    String createdBy;
    ArrayList<Cycle> cycles;

    public RoutineInfo(String name, String description, String category, String duration, String difficulty,String createdBy, ArrayList<Cycle> cycles) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.duration = duration;
        this.difficulty = difficulty;
        this.cycles = cycles;
        this.createdBy = createdBy;
    }
}
