package io.github.mat3e.task.vo;

import io.github.mat3e.DomainEvent;

import java.time.Instant;

public class TaskEvent implements DomainEvent {

    private final TaskSourceId id;
    private final Instant occurredOn;

    public TaskEvent(final TaskSourceId id, final Instant occurredOn) {
        this.id = id;
        this.occurredOn = occurredOn;
    }

    public TaskSourceId getId() {
        return id;
    }

    @Override
    public Instant getOccurredOn() {
        return occurredOn;
    }
}
