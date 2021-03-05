package io.github.mat3e.project;

class ProjectStepSnapshot {

    private final int id;
    private final String description;
    private final int daysToProjectDeadline;

    ProjectStepSnapshot(final int id, final String description, final int daysToProjectDeadline) {
        this.id = id;
        this.description = description;
        this.daysToProjectDeadline = daysToProjectDeadline;
    }

    int getId() {
        return id;
    }

    String getDescription() {
        return description;
    }

    int getDaysToProjectDeadline() {
        return daysToProjectDeadline;
    }
}
