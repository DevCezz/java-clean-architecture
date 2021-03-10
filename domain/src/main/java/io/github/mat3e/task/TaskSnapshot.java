package io.github.mat3e.task;

import io.github.mat3e.task.vo.TaskSourceId;

import java.time.ZonedDateTime;

class TaskSnapshot {

    private int id;
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    private TaskSourceId sourceId;

    TaskSnapshot() {}

    TaskSnapshot(final int id, final TaskSnapshot snapshot) {
        this.id = id;
        this.description = snapshot.description;
        this.done = snapshot.done;
        this.deadline = snapshot.deadline;
        this.changesCount = snapshot.changesCount;
        this.additionalComment = snapshot.additionalComment;
        this.sourceId = snapshot.sourceId;
    }

    TaskSnapshot(final int id, final String description, final boolean done, final ZonedDateTime deadline, final int changesCount, final String additionalComment, final TaskSourceId sourceId) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
        this.changesCount = changesCount;
        this.additionalComment = additionalComment;
        this.sourceId = sourceId;
    }

    int getId() {
        return id;
    }

    String getDescription() {
        return description;
    }

    boolean isDone() {
        return done;
    }

    ZonedDateTime getDeadline() {
        return deadline;
    }

    int getChangesCount() {
        return changesCount;
    }

    String getAdditionalComment() {
        return additionalComment;
    }

    TaskSourceId getSourceId() {
        return sourceId;
    }
}
