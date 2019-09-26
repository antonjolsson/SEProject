package com.example.tripplannr.model;

public class Trip {

    private String startTime;
    private String endTime;
    private long duration;
    private int changes;

    public Trip(String startTime, String endTime, long duration, int changes) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.changes = changes;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public long getDuration() {
        return duration;
    }

    public int getChanges() {
        return changes;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setChanges(int changes) {
        this.changes = changes;
    }
}
