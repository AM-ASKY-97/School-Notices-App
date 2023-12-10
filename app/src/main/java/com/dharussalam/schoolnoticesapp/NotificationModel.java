package com.dharussalam.schoolnoticesapp;

public class NotificationModel {

    String tittle, description;
    long timestamp;

    NotificationModel()
    {

    }

    public NotificationModel(String tittle, String description, long timestamp) {
        this.tittle = tittle;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
