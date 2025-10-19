package com.example.weeklyplanner.test.application;

import org.junit.Assert;
import org.junit.Test;

import com.example.weeklyplanner.application.ViewBacklogService;
import com.example.weeklyplanner.domain.model.Backlog;

public class TestViewBacklogService {

    @Test
    public void testViewBacklogService() {
        ViewBacklogService viewBacklogService = new ViewBacklogService();
        Backlog backlog = viewBacklogService.getBacklog();
        Assert.assertFalse("Backlog is empty", backlog.getTasks().isEmpty());
    }
}