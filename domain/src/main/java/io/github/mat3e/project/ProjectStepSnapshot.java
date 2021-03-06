package io.github.mat3e.project;

class ProjectStepSnapshot {

    private int id;
    private String description;
    private int daysToProjectDeadline;
    private boolean hasCorrespondingTask;
    private boolean isCorrespondingTaskDone;

    ProjectStepSnapshot() {}

    ProjectStepSnapshot(final int id, final String description, final int daysToProjectDeadline, final boolean hasCorrespondingTask, final boolean isCorrespondingTaskDone) {
        this.id = id;
        this.description = description;
        this.daysToProjectDeadline = daysToProjectDeadline;
        this.hasCorrespondingTask = hasCorrespondingTask;
        this.isCorrespondingTaskDone = isCorrespondingTaskDone;
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

    boolean hasCorrespondingTask() {
        return hasCorrespondingTask;
    }

    boolean isCorrespondingTaskDone() {
        return isCorrespondingTaskDone;
    }
}
