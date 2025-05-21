package com.fitnesstracker.model;

public class ProgressModel {
    // Type of progress being tracked (e.g., weight loss, muscle gain)
    private String progress_type;
    
    // Notes related to the progress entry
    private String progress_notes;
    
    // Date or timestamp of the progress log
    private String progress_log;
    
    // Weight before starting progress tracking
    private double before_wt;
    
    // Weight after progress tracking
    private double after_wt;
    
    // Full parameter constructor
    public ProgressModel(String progress_type, String progress_notes, String progress_log, double before_wt, double after_wt) {
        this.progress_type = progress_type;
        this.progress_notes = progress_notes;
        this.progress_log = progress_log;
        this.before_wt = before_wt;
        this.after_wt = after_wt;
    }
    
    // Constructor with only progress type
    public ProgressModel(String progress_type) {
        this.progress_type = progress_type;
    }

    // Getters and setters
    public String getProgress_type() {
        return progress_type;
    }

    public void setProgress_type(String progress_type) {
        this.progress_type = progress_type;
    }

    public String getProgress_notes() {
        return progress_notes;
    }

    public void setProgress_notes(String progress_notes) {
        this.progress_notes = progress_notes;
    }

    public String getProgress_log() {
        return progress_log;
    }

    public void setProgress_log(String progress_log) {
        this.progress_log = progress_log;
    }

    public double getBefore_wt() {
        return before_wt;
    }

    public void setBefore_wt(double before_wt) {
        this.before_wt = before_wt;
    }

    public double getAfter_wt() {
        return after_wt;
    }

    public void setAfter_wt(double after_wt) {
        this.after_wt = after_wt;
    }
}
