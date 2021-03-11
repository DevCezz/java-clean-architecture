package io.github.mat3e.project

import io.github.mat3e.project.dto.ProjectDto
import io.github.mat3e.project.dto.ProjectStepDto
import spock.lang.Specification
import spock.lang.Subject

class ProjectFactoryTest extends Specification {

    @Subject
    def factory = new ProjectFactory()

    def "should create Project from ProjectDto"() {
        given:
            ProjectDto dto = ProjectDto.create(67, "project", List.of(
                ProjectStepDto.create(91, "desc", -9)
            ))

        when:
            def result = factory.from(dto)

        then:
            with(result.snapshot) {
                it.id == dto.id
                it.name == dto.name
                it.steps[0].id == dto.steps[0].id
                it.steps[0].description == dto.steps[0].description
                it.steps[0].daysToProjectDeadline == dto.steps[0].daysToProjectDeadline
            }
    }
}
