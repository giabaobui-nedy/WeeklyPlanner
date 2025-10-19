package com.example.weeklyplanner.domain.port;

import java.sql.SQLException;
import java.util.List;
import com.example.weeklyplanner.domain.model.PlanEntry;

public interface PlanEntryRepository extends BaseRepository<PlanEntry> {
    List<PlanEntry> getEntriesByWeeklyPlanId(Integer weeklyPlanId) throws SQLException;
    List<PlanEntry> getEntriesByTaskId(Integer taskId) throws SQLException;
    List<PlanEntry> getEntriesByDayIndex(Integer dayIndex) throws SQLException;
}
