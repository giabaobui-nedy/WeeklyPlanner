package com.example.weeklyplanner.application;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import com.example.weeklyplanner.domain.model.Backlog;
import com.example.weeklyplanner.domain.model.Day;
import com.example.weeklyplanner.domain.model.DayPlan;
import com.example.weeklyplanner.domain.model.ISOWeek;
import com.example.weeklyplanner.domain.model.UserPreference;
import com.example.weeklyplanner.domain.model.WeeklyPlan;
import com.example.weeklyplanner.domain.port.UserPreferenceRepository;
import com.example.weeklyplanner.persistence.repository.UserPreferenceRepositoryImpl;

public class ViewWeeklyCalendarService {

    private final SavePlanService savePlanService;
    private final PlanWeekService planWeekService;
    private final ViewBacklogService viewBacklogService;
    private final UserPreferenceRepository userPreferenceRepository;

    public ViewWeeklyCalendarService() {
        this(new SavePlanService(), new PlanWeekService(), new ViewBacklogService(),
                new UserPreferenceRepositoryImpl());
    }

    ViewWeeklyCalendarService(SavePlanService savePlanService, PlanWeekService planWeekService,
            ViewBacklogService viewBacklogService, UserPreferenceRepository userPreferenceRepository) {
        this.savePlanService = savePlanService;
        this.planWeekService = planWeekService;
        this.viewBacklogService = viewBacklogService;
        this.userPreferenceRepository = userPreferenceRepository;
    }

    public WeeklyPlan getWeeklyPlan(ISOWeek isoWeek) {
        if (isoWeek == null) {
            throw new IllegalArgumentException("ISO week is required");
        }

        WeeklyPlan existingPlan = savePlanService.getWeeklyPlan(isoWeek);
        if (existingPlan != null && !existingPlan.getPlanEntries().isEmpty()) {
            return existingPlan;
        }

        viewBacklogService.refreshBacklog();
        Backlog backlog = viewBacklogService.getBacklog();

        if (backlog.getTasks().isEmpty()) {
            return existingPlan != null ? existingPlan : createEmptyPlan(isoWeek);
        }

        WeeklyPlan plannedWeek = planWeekService.planWeek(backlog, resolveUserPreference());
        if (plannedWeek == null) {
            return createEmptyPlan(isoWeek);
        }

        if (plannedWeek.getIsoWeek() != null) {
            ISOWeek plannedIso = plannedWeek.getIsoWeek();
            if (plannedIso.getYear() != isoWeek.getYear() || plannedIso.getWeek() != isoWeek.getWeek()) {
                savePlanService.saveWeeklyPlan(plannedWeek);
            }
        }

        return plannedWeek;
    }

    private UserPreference resolveUserPreference() {
        try {
            List<UserPreference> preferences = userPreferenceRepository.getAll();
            if (!preferences.isEmpty()) {
                return preferences.get(0);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load user preferences: " + e.getMessage(), e);
        }
        return new UserPreference(LocalTime.of(9, 0), LocalTime.of(17, 0));
    }

    private WeeklyPlan createEmptyPlan(ISOWeek isoWeek) {
        WeeklyPlan emptyPlan = new WeeklyPlan(isoWeek);
        for (Day day : Day.values()) {
            emptyPlan.getDayPlans().add(new DayPlan(day));
        }
        return emptyPlan;
    }
}
