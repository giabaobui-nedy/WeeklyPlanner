package com.example.weeklyplanner.application;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.weeklyplanner.domain.model.Day;
import com.example.weeklyplanner.domain.model.DayPlan;
import com.example.weeklyplanner.domain.model.ISOWeek;
import com.example.weeklyplanner.domain.model.WeeklyPlan;
import com.example.weeklyplanner.domain.port.WeeklyPlanRepository;
import com.example.weeklyplanner.persistence.repository.WeeklyPlanRepositoryImpl;

/**
 * Coordinates persistence of weekly plans and keeps an in-memory cache so they
 * can be retrieved without hitting the database every time.
 */
public class SavePlanService {

    private static final Map<String, WeeklyPlan> PLAN_CACHE = new ConcurrentHashMap<>();

    private final WeeklyPlanRepository weeklyPlanRepository;

    public SavePlanService() {
        this(new WeeklyPlanRepositoryImpl());
    }

    public SavePlanService(WeeklyPlanRepository weeklyPlanRepository) {
        this.weeklyPlanRepository = weeklyPlanRepository;
    }

    public WeeklyPlan saveWeeklyPlan(WeeklyPlan weeklyPlan) {
        if (weeklyPlan == null || weeklyPlan.getIsoWeek() == null) {
            throw new IllegalArgumentException("Weekly plan with ISO week information is required");
        }

        try {
            weeklyPlanRepository.add(weeklyPlan);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to persist weekly plan: " + e.getMessage(), e);
        }

        PLAN_CACHE.put(cacheKey(weeklyPlan.getIsoWeek()), weeklyPlan);
        return weeklyPlan;
    }

    public WeeklyPlan getWeeklyPlan(ISOWeek isoWeek) {
        if (isoWeek == null) {
            throw new IllegalArgumentException("ISO week is required");
        }

        WeeklyPlan cachedPlan = PLAN_CACHE.get(cacheKey(isoWeek));
        if (cachedPlan != null) {
            return cachedPlan;
        }

        WeeklyPlan persistedPlan = fetchFromRepository(isoWeek);
        if (persistedPlan != null) {
            PLAN_CACHE.put(cacheKey(isoWeek), persistedPlan);
        }
        return persistedPlan;
    }

    private WeeklyPlan fetchFromRepository(ISOWeek isoWeek) {
        try {
            List<WeeklyPlan> plans = weeklyPlanRepository.getPlansByYearAndWeek(isoWeek.getYear(), isoWeek.getWeek());
            if (plans.isEmpty()) {
                return null;
            }
            WeeklyPlan plan = plans.get(0);
            ensureDayPlans(plan);
            return plan;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load weekly plan: " + e.getMessage(), e);
        }
    }

    private void ensureDayPlans(WeeklyPlan plan) {
        if (plan.getDayPlans().isEmpty()) {
            for (Day day : Day.values()) {
                plan.getDayPlans().add(new DayPlan(day));
            }
        }
    }

    private String cacheKey(ISOWeek isoWeek) {
        return isoWeek.getYear() + "-" + isoWeek.getWeek();
    }
}
