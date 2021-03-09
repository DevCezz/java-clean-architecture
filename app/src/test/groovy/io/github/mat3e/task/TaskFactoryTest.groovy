package io.github.mat3e.task

import io.github.mat3e.task.dto.TaskDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class TaskFactoryTest extends Specification {

    @Subject
    def factory = new TaskFactory()

    def "should create Task from TaskDto"() {
        given:
            TaskDto dto = new TaskDto(49, "desc", true, ZonedDateTime.now(), "foo")

        when:
            def result = factory.from(dto)

        then:
            with(result.snapshot) {
                it.id == dto.id
                it.description == dto.description
                it.done == dto.done
                it.deadline == dto.deadline
                it.additionalComment == dto.additionalComment
            }
    }
}
