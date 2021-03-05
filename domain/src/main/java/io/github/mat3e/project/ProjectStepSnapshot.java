package io.github.mat3e.project;

class ProjectStepSnapshot {

    private final int id;
    private final String description;
    private final int daysToProjectDeadline;
    private final ProjectSnapshot project;

    ProjectStepSnapshot(final int id, final String description, final int daysToProjectDeadline, final ProjectSnapshot project) {
        this.id = id;
        this.description = description;
        this.daysToProjectDeadline = daysToProjectDeadline;
        this.project = project;
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

    ProjectSnapshot getProject() {
        return project;
    }
}
