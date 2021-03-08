package io.github.mat3e.task

import io.github.mat3e.task.vo.TaskSourceId
import spock.lang.Specification

import java.time.ZoneId
import java.time.ZonedDateTime

class TaskTest extends Specification {

    def "should restore task from task snapshot"() {
        given:
            def snapshot = new TaskSnapshot(
                    12,
                    "easy task",
                    true,
                    ZonedDateTime.of(2002, 10, 12, 12, 23, 45, 10, ZoneId.of("Europe/Warsaw")),
                    5,
                    "do alone",
                    new TaskSourceId("90")
            )

        when:
            def result = Task.restore(snapshot).getSnapshot()

        then:
            result.id == snapshot.id
            result.description == snapshot.description
            result.done == snapshot.done
            result.deadline == snapshot.deadline
            result.changesCount == snapshot.changesCount
            result.additionalComment == snapshot.additionalComment
            result.sourceId.id == snapshot.sourceId.id
    }
}
