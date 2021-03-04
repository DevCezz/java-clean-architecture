package io.github.mat3e.task;

import io.github.mat3e.project.dto.SimpleProject;

import java.time.ZonedDateTime;

class Task {

    static Task restore(TaskSnapshot snapshot) {
        return new Task(
                snapshot.getId(),
                snapshot.getDescription(),
                snapshot.isDone(),
                snapshot.getDeadline(),
                snapshot.getChangesCount(),
                snapshot.getAdditionalComment(),
                snapshot.getProject() != null ? SimpleProject.restore(snapshot.getProject()) : null
        );
    }

    private final int id;
    private final String description;
    private final boolean done;
    private final ZonedDateTime deadline;
    private final int changesCount;
    private final String additionalComment;
    private final SimpleProject project;

    private Task(final int id, final String description, final boolean done, final ZonedDateTime deadline, final int changesCount, final String additionalComment, final SimpleProject project) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
        this.changesCount = changesCount;
        this.additionalComment = additionalComment;
        this.project = project;
    }

    void toggle() {
        done = !done;
        ++changesCount;
    }

    void updateInfo(String description, ZonedDateTime deadline, String additionalComment) {
        this.description = description;
        this.deadline = deadline;
        this.additionalComment = additionalComment;
    }

    TaskSnapshot getSnapshot() {
        return new TaskSnapshot(id, description, done, deadline, changesCount, additionalComment, project != null ? project.getSnapshot() : null);
    }
}
