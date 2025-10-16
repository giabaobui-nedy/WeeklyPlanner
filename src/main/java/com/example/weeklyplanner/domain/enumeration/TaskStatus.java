package com.example.weeklyplanner.domain.enumeration;

public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE;
    
    @Override
    public String toString() {
        switch (this) {
            case IN_PROGRESS:
                return "In Progress";
            case TODO:
                return "To Do";
            case DONE:
                return "Done";
            default:
                return super.toString();
        }
    }
}
