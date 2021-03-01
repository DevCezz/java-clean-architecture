package io.github.mat3e.task.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@JsonDeserialize(builder = TaskDto.Builder.class)
public class TaskDto {

    public static Builder builder() {
        return new Builder();
    }

    private final int id;
    @NotNull
    private final String description;
    private final boolean done;
    private final ZonedDateTime deadline;
    private final String additionalComment;

    public TaskDto(
            final int id,
            @NotNull final String description,
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

    @JsonPOJOBuilder
    public static class Builder {
        private int id;
        @NotNull
        private String description;
        private boolean done;
        private ZonedDateTime deadline;
        private String additionalComment;

        private Builder() {}

        public TaskDto build() {
            return new TaskDto(this);
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
