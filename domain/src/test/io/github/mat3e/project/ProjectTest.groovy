package io.github.mat3e.project

import spock.lang.Specification

import static io.github.mat3e.project.Project.Step
import static io.github.mat3e.project.ProjectFixture.*

class ProjectTest extends Specification {

    def "should restore project from project snapshot"() {
        given:
            def snapshot = projectSnapshotWith3Steps()

        when:
            def result = Project.restore snapshot getSnapshot()

        then:
            result.id == snapshot.id
            result.name == snapshot.name
            result.steps.size() == 3
    }

    def "should add step to existing project when it does not contain one"() {
        given:
            def project = Project.restore projectSnapshotWithoutSteps()
            def step = Step.restore projectStepSnapshot()

        when:
            project.addStep(step)

        then:
            with(project.snapshot) {
                it.steps.size() == 1
            }
    }

    def "should not add another step to existing project when it contains this one"() {
        given:
            def project = Project.restore projectSnapshotWithoutSteps()

        when:
            project.addStep(Step.restore(projectStepSnapshot()))
            project.addStep(Step.restore(projectStepSnapshot()))

        then:
            with(project.snapshot) {
                it.steps.size() == 1
            }
    }

    def "should remove step from existing project when it contains this one"() {
        given:
            def project = Project.restore projectSnapshotWithoutSteps()
            project.addStep(Step.restore(projectStepSnapshot()))

        when:
            project.removeStep(Step.restore(projectStepSnapshot()))

        then:
            with(project.snapshot) {
                it.steps.size() == 0
            }
    }

    def "should not remove step from existing project when it does not contain this one"() {
        given:
            def project = Project.restore projectSnapshotWithoutSteps()

        when:
            project.removeStep(Step.restore(projectStepSnapshot()))

        then:
            with(project.snapshot) {
                it.steps.size() == 0
            }
    }
}
