package io.github.mat3e.task.dto;

import java.time.ZonedDateTime;

interface TaskWithChangesDto {

    int getId();

    String getDescription();

    boolean isDone();

    ZonedDateTime getDeadline();

    int getChangesCount();
}
