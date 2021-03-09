package io.github.mat3e.task;

import io.github.mat3e.task.vo.TaskCreator;
import io.github.mat3e.task.vo.TaskSourceId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

class TaskFixture {

    private static final LocalDate DATE = LocalDate.of(2002, 10, 12);
    private static final LocalTime TIME = LocalTime.of(12, 23);
    private static final ZonedDateTime DEADLINE = ZonedDateTime.of(DATE, TIME, ZoneId.of("Europe/Warsaw"));

    static TaskSnapshot doneTaskSnapshot() {
        return new TaskSnapshot(12, "easy", true, DEADLINE, 5, "empty", sourceId("90"));
    }

    static TaskSnapshot undoneTaskSnapshot() {
        return new TaskSnapshot(12, "easy", false, DEADLINE, 5, "empty", sourceId("90"));
    }

    static TaskCreator taskCreator() {
        return new TaskCreator(sourceId("15"), "long desc", DEADLINE);
    }

    private static TaskSourceId sourceId(String id) {
        return new TaskSourceId(id);
    }
}
