package org.example;

public class UserAction {
    private int day;
    private int month;
    private int year;
    private String time;
    private int actionType;

    public UserAction(int day, int month, int year, String time, int actionType) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;
        this.actionType = actionType;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return String.format("Дата: %d-%02d-%d Время: %s Тип действия: %d",
                year, month, day, time, actionType);
    }



}
