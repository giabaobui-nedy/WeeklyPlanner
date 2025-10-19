package com.example.weeklyplanner.domain.port;
import com.example.weeklyplanner.domain.model.UserPreference;

public interface UserPreferenceRepository extends BaseRepository<UserPreference> {
    // For now, just the base CRUD operations
    // Future: could add methods like getDefaultPreference() if needed
}
