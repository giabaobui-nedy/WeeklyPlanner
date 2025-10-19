package com.example.weeklyplanner.persistence.repository;

import com.example.weeklyplanner.domain.model.WeeklyPlan;
import com.example.weeklyplanner.domain.model.UserPreference;
import com.example.weeklyplanner.domain.model.PlanEntry;
import com.example.weeklyplanner.domain.model.DayPlan;
import com.example.weeklyplanner.domain.model.Day;
import com.example.weeklyplanner.domain.port.WeeklyPlanRepository;
import com.example.weeklyplanner.persistence.db.DatabaseConfigurator;
import com.example.weeklyplanner.persistence.entity.WeeklyPlanEntity;
import com.example.weeklyplanner.persistence.entity.UserPreferenceEntity;
import com.example.weeklyplanner.persistence.entity.PlanEntryEntity;
import com.example.weeklyplanner.persistence.mapper.WeeklyPlanMapper;
import com.example.weeklyplanner.persistence.mapper.UserPreferenceMapper;
import com.example.weeklyplanner.persistence.mapper.PlanEntryMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class WeeklyPlanRepositoryImpl implements WeeklyPlanRepository {
    private final Dao<WeeklyPlanEntity, Integer> weeklyPlanDao;
    private final Dao<UserPreferenceEntity, Integer> userPreferenceDao;
    private final Dao<PlanEntryEntity, Integer> planEntryDao;

    public WeeklyPlanRepositoryImpl() {
        try (JdbcConnectionSource jcs = DatabaseConfigurator.getDatabaseConnectionSource()) {
            weeklyPlanDao = DaoManager.createDao(jcs, WeeklyPlanEntity.class);
            userPreferenceDao = DaoManager.createDao(jcs, UserPreferenceEntity.class);
            planEntryDao = DaoManager.createDao(jcs, PlanEntryEntity.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WeeklyPlan> getAll() throws SQLException {
        QueryBuilder<WeeklyPlanEntity, Integer> queryBuilder = weeklyPlanDao.queryBuilder();
        queryBuilder.orderBy("id", false);
        try {
            List<WeeklyPlanEntity> rows = queryBuilder.query();
            WeeklyPlanMapper mapper = new WeeklyPlanMapper();
            return mapper.convertEntityListToDomainList(rows);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public WeeklyPlan getOne(Integer id) throws SQLException {
        try {
            WeeklyPlanEntity entity = weeklyPlanDao.queryForId(id);
            return new WeeklyPlanMapper().convertEntityToDomain(entity);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public WeeklyPlan add(WeeklyPlan entity) throws SQLException {
        try {
            WeeklyPlanEntity weeklyPlanEntity = new WeeklyPlanMapper().convertDomainToEntity(entity);
            weeklyPlanDao.create(weeklyPlanEntity);
            return new WeeklyPlanMapper().convertEntityToDomain(weeklyPlanEntity);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public WeeklyPlan update(WeeklyPlan entity) throws SQLException {
        try {
            WeeklyPlanEntity weeklyPlanEntity = new WeeklyPlanMapper().convertDomainToEntity(entity);
            weeklyPlanDao.update(weeklyPlanEntity);
            return new WeeklyPlanMapper().convertEntityToDomain(weeklyPlanEntity);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public WeeklyPlan delete(Integer id) throws SQLException {
        try {
            WeeklyPlanEntity entity = weeklyPlanDao.queryForId(id);
            if (entity != null) {
                weeklyPlanDao.delete(entity);
                return new WeeklyPlanMapper().convertEntityToDomain(entity);
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<WeeklyPlan> getPlansByYearAndWeek(Integer year, Integer week) throws SQLException {
        QueryBuilder<WeeklyPlanEntity, Integer> queryBuilder = weeklyPlanDao.queryBuilder();
        queryBuilder.where().eq("year", year).and().eq("week", week);
        try {
            List<WeeklyPlanEntity> rows = queryBuilder.query();
            WeeklyPlanMapper mapper = new WeeklyPlanMapper();
            return mapper.convertEntityListToDomainList(rows);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<WeeklyPlan> getPlansByVersion(String version) throws SQLException {
        QueryBuilder<WeeklyPlanEntity, Integer> queryBuilder = weeklyPlanDao.queryBuilder();
        queryBuilder.where().eq("version", version);
        try {
            List<WeeklyPlanEntity> rows = queryBuilder.query();
            WeeklyPlanMapper mapper = new WeeklyPlanMapper();
            return mapper.convertEntityListToDomainList(rows);
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Generate a complete weekly plan with user preferences and plan entries.
     * This method creates a proper plan by:
     * 1. Getting the user preference
     * 2. Creating a weekly plan
     * 3. Adding plan entries for each day
     * 4. Building the complete plan structure
     */
    public WeeklyPlan generateWeeklyPlan(Integer year, Integer week) throws SQLException {
        try {
            // 1. Get the user preference (use the first one available)
            UserPreferenceEntity userPrefEntity = userPreferenceDao.queryForFirst();
            if (userPrefEntity == null) {
                throw new SQLException("No user preference found. Please create a user preference first.");
            }
            
            UserPreferenceMapper userPrefMapper = new UserPreferenceMapper();
            UserPreference userPreference = userPrefMapper.convertEntityToDomain(userPrefEntity);
            
            // 2. Create the weekly plan
            WeeklyPlan weeklyPlan = new WeeklyPlan(new com.example.weeklyplanner.domain.model.ISOWeek(year, week));
            
            // 3. Create day plans for each day of the week
            Day[] days = Day.values();
            for (int i = 0; i < days.length; i++) {
                DayPlan dayPlan = new DayPlan(days[i]);
                
                // 4. Get plan entries for this day
                QueryBuilder<PlanEntryEntity, Integer> planEntryQuery = planEntryDao.queryBuilder();
                planEntryQuery.where().eq("dayIndex", i + 1); // dayIndex is 1-based
                List<PlanEntryEntity> planEntryEntities = planEntryQuery.query();
                
                // 5. Convert plan entries to domain objects and add to day plan
                PlanEntryMapper planEntryMapper = new PlanEntryMapper();
                for (PlanEntryEntity planEntryEntity : planEntryEntities) {
                    PlanEntry planEntry = planEntryMapper.convertEntityToDomain(planEntryEntity);
                    dayPlan.getTaskEntries().add(planEntry);
                }
                
                weeklyPlan.getDayPlans().add(dayPlan);
            }
            
            return weeklyPlan;
            
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Create a new weekly plan with user preferences and save it to the database.
     * This is a more practical method that handles the complex relationships.
     */
    public WeeklyPlan createWeeklyPlan(Integer year, Integer week) throws SQLException {
        try {
            // 1. Get or create user preference
            UserPreferenceEntity userPrefEntity = userPreferenceDao.queryForFirst();
            if (userPrefEntity == null) {
                // Create a default user preference
                userPrefEntity = new UserPreferenceEntity();
                userPrefEntity.setDayStart("09:00");
                userPrefEntity.setDayEnd("17:00");
                
                // Set required timestamp fields
                String currentTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                userPrefEntity.setCreatedAt(currentTime);
                userPrefEntity.setUpdatedAt(currentTime);
                
                userPreferenceDao.create(userPrefEntity);
            }
            
            // 2. Create weekly plan entity
            WeeklyPlanEntity weeklyPlanEntity = new WeeklyPlanEntity();
            weeklyPlanEntity.setYear(year);
            weeklyPlanEntity.setWeek(week);
            weeklyPlanEntity.setVersion("1.0");
            weeklyPlanEntity.setUserPreference(userPrefEntity);
            
            // Set required timestamp fields
            String currentTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            weeklyPlanEntity.setCreatedAt(currentTime);
            weeklyPlanEntity.setUpdatedAt(currentTime);
            
            weeklyPlanDao.create(weeklyPlanEntity);
            
            // 3. Convert to domain object and populate day plans
            WeeklyPlanMapper mapper = new WeeklyPlanMapper();
            WeeklyPlan weeklyPlan = mapper.convertEntityToDomain(weeklyPlanEntity);
            
            // 4. Populate day plans for all 7 days of the week
            Day[] days = Day.values();
            for (Day day : days) {
                DayPlan dayPlan = new DayPlan(day);
                weeklyPlan.getDayPlans().add(dayPlan);
            }
            
            return weeklyPlan;
            
        } catch (SQLException e) {
            throw e;
        }
    }
}
