package io.github.mat3e.task.vo;

import io.github.mat3e.DomainEvent;

import java.time.Instant;
import java.time.ZonedDateTime;

public class TaskEvent implements DomainEvent {

    public enum State {
        DONE, UNDONE, UPDATED, DELETED
    }

    private final TaskSourceId id;
    private final Instant occurredOn;
    private final Data data;
    private final State state;

    public TaskEvent(final TaskSourceId id, final State state, final Data data) {
        this.id = id;
        this.occurredOn = Instant.now();
        this.state = state;
        this.data = data;
    }

    public TaskSourceId getId() {
        return id;
    }

    @Override
    public Instant getOccurredOn() {
        return occurredOn;
    }

    public Data getData() {
        return data;
    }

    public State getState() {
        return state;
    }

    public static class Data {
        private final String description;
        private final ZonedDateTime deadline;
        private final String additionalComment;

        public Data(final String description, final ZonedDateTime deadline, final String additionalComment) {
            this.description = description;
            this.deadline = deadline;
            this.additionalComment = additionalComment;
        }

        String getDescription() {
            return description;
        }

        ZonedDateTime getDeadline() {
            return deadline;
        }

        String getAdditionalComment() {
            return additionalComment;
        }
    }
}
