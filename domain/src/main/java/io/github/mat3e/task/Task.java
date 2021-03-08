package io.github.mat3e.task;

import io.github.mat3e.task.vo.TaskCreator;
import io.github.mat3e.task.vo.TaskEvent;
import io.github.mat3e.task.vo.TaskSourceId;

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
                snapshot.getSourceId()
        );
    }

    static Task createFrom(final TaskCreator source) {
        return new Task(
                source.getDescription(),
                source.getDeadline(),
                source.getSourceId()
        );
    }

    private int id;
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    private final TaskSourceId sourceId;

    private Task(final int id, final String description, final boolean done, final ZonedDateTime deadline, final int changesCount, final String additionalComment, final TaskSourceId sourceId) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
        this.changesCount = changesCount;
        this.additionalComment = additionalComment;
        this.sourceId = sourceId;
    }

    private Task(final String description, final ZonedDateTime deadline, final TaskSourceId sourceId) {
        this.description = description;
        this.deadline = deadline;
        this.sourceId = sourceId;
    }

    TaskEvent toggle() {
        done = !done;
        ++changesCount;
        return new TaskEvent(
                sourceId,
                done ? TaskEvent.State.DONE : TaskEvent.State.UNDONE,
                null
        );
    }

    TaskEvent updateInfo(String description, ZonedDateTime deadline, String additionalComment) {
        this.description = description;
        this.deadline = deadline;
        this.additionalComment = additionalComment;
        return new TaskEvent(
                sourceId,
                TaskEvent.State.UPDATED,
                new TaskEvent.Data(
                        description,
                        deadline,
                        additionalComment
                )
        );
    }

    TaskSnapshot getSnapshot() {
        return new TaskSnapshot(id, description, done, deadline, changesCount, additionalComment, sourceId);
    }
}
