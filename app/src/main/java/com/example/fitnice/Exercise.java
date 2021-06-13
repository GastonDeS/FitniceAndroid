package com.example.fitnice;

public class Exercise {
    private String exerciseName;
    private String description;
    private String durationS;
    private String durationR;

    public Exercise(String exerciseName, String description, String durationS, String durationR) {
        this.exerciseName = exerciseName;
        this.description = description;
        this.durationS = durationS;
        this.durationR = durationR;
    }

    public Exercise(String exerciseName, String description, String durationS) {
        this.exerciseName = exerciseName;
        this.description = description;
        this.durationS = durationS;
    }

    public String getDurationS() {
        return durationS;
    }

    public void setDurationS(String durationS) {
        this.durationS = durationS;
    }

    public String getDurationR() {
        return durationR;
    }

    public void setDurationR(String durationR) {
        this.durationR = durationR;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }
}
