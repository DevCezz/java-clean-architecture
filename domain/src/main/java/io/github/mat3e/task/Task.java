package io.github.mat3e.task;

import io.github.mat3e.project.dto.SimpleProjectQueryEntity;

import java.time.ZonedDateTime;

class Task {
    private int id;
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    private SimpleProjectQueryEntity project;

    Task(final String description, final ZonedDateTime deadline, final SimpleProjectQueryEntity project) {
        this.description = description;
        this.deadline = deadline;
        this.project = project;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    boolean isDone() {
        return done;
    }

    void setDone(boolean done) {
        this.done = done;
    }

    ZonedDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }

    int getChangesCount() {
        return changesCount;
    }

    void setChangesCount(int changesCount) {
        this.changesCount = changesCount;
    }

    String getAdditionalComment() {
        return additionalComment;
    }

    void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }

    SimpleProjectQueryEntity getProject() {
        return project;
    }

    void setProject(SimpleProjectQueryEntity project) {
        this.project = project;
    }
}
