package com.example.weeklyplanner.domain.model;

public class ISOWeek {
    private final int year;
    private final int week;

    public ISOWeek(int year, int week) {
        this.year = year;
        this.week = week;
    }

    public int getYear() {
        return year;
    }

    public int getWeek() {
        return week;
    }
}
