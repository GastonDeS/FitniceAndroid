package com.example.fitnice.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Executions {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private Long date;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("wasModified")
    @Expose
    private Boolean wasModified;
    @SerializedName("routine")
    @Expose
    private Routine routine;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getWasModified() {
        return wasModified;
    }

    public void setWasModified(Boolean wasModified) {
        this.wasModified = wasModified;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

}