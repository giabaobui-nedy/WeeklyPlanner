package com.example.weeklyplanner.test.persistence.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.weeklyplanner.domain.enumeration.TaskPriority;
import com.example.weeklyplanner.domain.enumeration.TaskStatus;
import com.example.weeklyplanner.domain.model.Task;
import com.example.weeklyplanner.domain.model.WeeklyPlan;
import com.example.weeklyplanner.persistence.db.DatabaseConfigurator;
import com.example.weeklyplanner.persistence.entity.PlanEntryEntity;
import com.example.weeklyplanner.persistence.entity.TaskEntity;
import com.example.weeklyplanner.persistence.entity.WeeklyPlanEntity;
import com.example.weeklyplanner.persistence.repository.TaskRepositoryImpl;
import com.example.weeklyplanner.persistence.repository.WeeklyPlanRepositoryImpl;
import com.example.weeklyplanner.persistence.repository.PlanEntryRepositoryImpl;
import com.example.weeklyplanner.domain.model.PlanEntry;
import com.example.weeklyplanner.test.persistence.TestDataManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

public class PlanEntryRepositoryIntegrationTest {

    private TaskRepositoryImpl taskRepository;
    private WeeklyPlanRepositoryImpl weeklyPlanRepository;
    private PlanEntryRepositoryImpl planEntryRepository;

    @Before
    public void setUp() throws Exception {
        TestDataManager.initialize();
        taskRepository = new TaskRepositoryImpl();
        weeklyPlanRepository = new WeeklyPlanRepositoryImpl();
        planEntryRepository = new PlanEntryRepositoryImpl();
    }

    @Test
    public void testPlanEntryPersistsTaskAndPlanReferences() throws Exception {
        Task persistedTask = taskRepository.add(new Task(null, "Integration Task", "Plan entry linkage test",
                TaskStatus.TODO, TaskPriority.HIGH, "Integration",
                LocalDate.now().plusDays(1), 60));
        Assert.assertNotNull("Task should be persisted with an ID", persistedTask.getId());

        int year = 2099;
        int week = 1;
        WeeklyPlan weeklyPlan = weeklyPlanRepository.createWeeklyPlan(year, week);
        Assert.assertNotNull("Weekly plan should be created", weeklyPlan);

        try (JdbcConnectionSource connection = DatabaseConfigurator.getDatabaseConnectionSource()) {
            Dao<TaskEntity, Integer> taskDao = DaoManager.createDao(connection, TaskEntity.class);
            Dao<WeeklyPlanEntity, Integer> weeklyPlanDao = DaoManager.createDao(connection, WeeklyPlanEntity.class);
            Dao<PlanEntryEntity, Integer> planEntryDao = DaoManager.createDao(connection, PlanEntryEntity.class);

            TaskEntity taskEntity = taskDao.queryForId(persistedTask.getId());
            Assert.assertNotNull("Persisted task entity should be retrievable", taskEntity);

            WeeklyPlanEntity planEntity = weeklyPlanDao.queryBuilder()
                    .where()
                    .eq("year", year)
                    .and()
                    .eq("week", week)
                    .queryForFirst();
            Assert.assertNotNull("Persisted weekly plan entity should be retrievable", planEntity);

            LocalTime start = LocalTime.of(9, 0);
            LocalTime end = start.plusMinutes(persistedTask.getDurationMinutes());
            PlanEntryEntity planEntryEntity = new PlanEntryEntity();
            planEntryEntity.setTask(taskEntity);
            planEntryEntity.setPlan(planEntity);
            planEntryEntity.setDayIndex(1);
            planEntryEntity.setDurationMinutes(persistedTask.getDurationMinutes());
            planEntryEntity.setStartHour(start.getHour());
            planEntryEntity.setStartMinute(start.getMinute());
            planEntryEntity.setEndHour(end.getHour());
            planEntryEntity.setEndMinute(end.getMinute());

            planEntryDao.create(planEntryEntity);
            Assert.assertNotNull("Plan entry should receive an ID", planEntryEntity.getId());

            PlanEntryEntity storedEntity = planEntryDao.queryForId(planEntryEntity.getId());
            Assert.assertNotNull("Stored plan entry should be retrievable", storedEntity);
            Assert.assertNotNull("Stored plan entry should reference a task", storedEntity.getTask());
            Assert.assertNotNull("Stored plan entry should reference a weekly plan", storedEntity.getPlan());
            Assert.assertEquals("Task linkage should match", taskEntity.getId(), storedEntity.getTask().getId());
            Assert.assertEquals("Plan linkage should match", planEntity.getId(), storedEntity.getPlan().getId());

            List<PlanEntry> entries = planEntryRepository.getEntriesByWeeklyPlanId(planEntity.getId());
            Assert.assertFalse("Repository should retrieve stored plan entry", entries.isEmpty());
            PlanEntry domainEntry = entries.stream()
                    .filter(entry -> entry.getTaskId() != null && entry.getTaskId().equals(taskEntity.getId()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Stored plan entry not found for task " + taskEntity.getId()));
            Assert.assertEquals("Domain plan entry should expose task ID", taskEntity.getId(), domainEntry.getTaskId());
            Assert.assertEquals("Domain plan entry should expose weekly plan ID", planEntity.getId(),
                    domainEntry.getDayPlanId());
        }
    }
}
