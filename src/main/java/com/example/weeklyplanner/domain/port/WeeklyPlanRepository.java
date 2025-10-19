package com.example.weeklyplanner.domain.port;

import java.sql.SQLException;
import java.util.List;
import com.example.weeklyplanner.domain.model.WeeklyPlan;

public interface WeeklyPlanRepository extends BaseRepository<WeeklyPlan> {
    List<WeeklyPlan> getPlansByYearAndWeek(Integer year, Integer week) throws SQLException;
    List<WeeklyPlan> getPlansByVersion(String version) throws SQLException;
}
