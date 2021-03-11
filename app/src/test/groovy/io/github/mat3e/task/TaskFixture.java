package io.github.mat3e.task;

import io.github.mat3e.task.dto.TaskDto;
import io.github.mat3e.task.vo.TaskCreator;
import io.github.mat3e.task.vo.TaskSourceId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

class TaskFixture {

    public static final ZonedDateTime DATETIME = ZonedDateTime.of(
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

    static TaskDto doneTaskDtoWithId(int id, String description, ZonedDateTime deadline, String comment) {
        return new TaskDto(id, description, true, deadline, comment);
    }

    static TaskDto undoneTaskDtoWithId(int id, String description, ZonedDateTime deadline, String comment) {
        return new TaskDto(id, description, false, deadline, comment);
    }

    static Task doneTask(String description, ZonedDateTime deadline, String comment) {
        return Task.restore(new TaskSnapshot(0, description, true, deadline, 10, comment, new TaskSourceId("97")));
    }

    static Task undoneTask(String description, ZonedDateTime deadline, String comment) {
        return Task.restore(new TaskSnapshot(0, description, false, deadline, 10, comment, new TaskSourceId("97")));
    }
}
