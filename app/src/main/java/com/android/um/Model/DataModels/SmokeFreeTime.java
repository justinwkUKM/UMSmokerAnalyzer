package com.android.um.Model.DataModels;

import java.util.Date;

public class SmokeFreeTime {

    long hour=0;
    long minutes=0;
    long seconds=0;
    Date startDate;
    boolean smokeFreeCompleted=false;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public boolean isSmokeFreeCompleted() {
        return smokeFreeCompleted;
    }

    public void setSmokeFreeCompleted(boolean smokeFreeCompleted) {
        this.smokeFreeCompleted = smokeFreeCompleted;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }
}
