package io.github.mat3e.task.dto;

import java.time.ZonedDateTime;
import java.util.Objects;

public class TaskDto {

    public static Builder builder() {
        return new Builder();
    }

    private final int id;
    private final String description;
    private final boolean done;
    private final ZonedDateTime deadline;
    private final String additionalComment;

    public TaskDto(
            final int id,
            final String description,
            final boolean done,
            final ZonedDateTime deadline,
            final String additionalComment
    ) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
        this.additionalComment = additionalComment;
    }

    public Builder toBuilder() {
        return builder()
                .withId(id)
                .withDescription(description)
                .withDone(done)
                .withDeadline(deadline)
                .withAdditionalComment(additionalComment);
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TaskDto taskDto = (TaskDto) o;
        return id == taskDto.id &&
                done == taskDto.done &&
                description.equals(taskDto.description) &&
                Objects.equals(deadline, taskDto.deadline) &&
                Objects.equals(additionalComment, taskDto.additionalComment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, done, deadline, additionalComment);
    }

    public static class Builder {
        private int id;
        private String description;
        private boolean done;
        private ZonedDateTime deadline;
        private String additionalComment;

        private Builder() {}

        public TaskDto build() {
            return new TaskDto(id, description, done, deadline, additionalComment);
        }

        public Builder withId(final int id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(final String description) {
            this.description = description;
            return this;
        }

        public Builder withDone(final boolean done) {
            this.done = done;
            return this;
        }

        public Builder withDeadline(final ZonedDateTime deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder withAdditionalComment(final String additionalComment) {
            this.additionalComment = additionalComment;
            return this;
        }
    }
}
