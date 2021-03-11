package io.github.mat3e.task;

import io.github.mat3e.task.dto.TaskDto;
import io.github.mat3e.task.vo.TaskCreator;
import io.github.mat3e.task.vo.TaskSourceId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

class TaskFixture {

    private static final ZonedDateTime DATETIME = ZonedDateTime.of(
            LocalDate.of(2020, 12, 16),
            LocalTime.of(14, 45),
            ZoneId.of("Europe/Warsaw")
    );

    static TaskCreator taskCreator() {
        return new TaskCreator(new TaskSourceId("1"), "desc1", DATETIME);
    }

    static TaskDto taskDto() {
        return new TaskDto(14, "desc", true, DATETIME, "foo");
    }
}
