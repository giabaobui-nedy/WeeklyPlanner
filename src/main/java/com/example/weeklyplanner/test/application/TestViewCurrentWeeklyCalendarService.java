package com.example.weeklyplanner.test.application;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import com.example.weeklyplanner.application.ViewWeeklyCalendarService;
import com.example.weeklyplanner.domain.model.ISOWeek;
import com.example.weeklyplanner.domain.model.WeeklyPlan;

public class TestViewCurrentWeeklyCalendarService {
    @Test
    public void testViewCurrentWeeklyCalendarService() {
        ViewWeeklyCalendarService viewCurrentWeeklyCalendarService = new ViewWeeklyCalendarService();
        ISOWeek isoWeek = new ISOWeek(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
        WeeklyPlan weeklyPlan = viewCurrentWeeklyCalendarService.getWeeklyPlan(isoWeek);
        Assert.assertFalse("Weekly plan is empty", weeklyPlan.getPlanEntries().isEmpty());
    }
}
