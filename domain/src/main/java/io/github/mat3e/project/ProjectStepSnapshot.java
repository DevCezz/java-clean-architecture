package io.github.mat3e.project;

class ProjectStepSnapshot {

    private int id;
    private String description;
    private int daysToProjectDeadline;

    ProjectStepSnapshot() {}

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
