package io.github.mat3e.task;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public class TaskDto {
    private int id;
    @NotNull
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private String additionalComment;

    private TaskDto(final Builder builder) {
        id = builder.id;
        description = builder.description;
        done = builder.done;
        deadline = builder.deadline;
        additionalComment = builder.additionalComment;
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    void setDone(boolean done) {
        this.done = done;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }

    public static class Builder {
        private int id;
        @NotNull
        private String description;
        private boolean done;
        private ZonedDateTime deadline;
        private String additionalComment;

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
