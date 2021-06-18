
package com.example.fitnice.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExecutionsSend {

    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("wasModified")
    @Expose
    private Boolean wasModified;

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

}