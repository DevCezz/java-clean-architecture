package io.github.mat3e.task;

import io.github.mat3e.project.dto.SimpleProject;

import java.time.ZonedDateTime;

class Task {
    private int id;
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    private final SimpleProject project;

    Task(final String description, final ZonedDateTime deadline, final SimpleProject project) {
        this.description = description;
        this.deadline = deadline;
        this.project = project;
    }

    void toggle() {
        done = !done;
        ++changesCount;
    }
}
