package io.github.mat3e.project


import spock.lang.Specification

class ProjectTest extends Specification {

    def "should restore project from project snapshot"() {
        given:
            def snapshot = ProjectFixture.projectSnapshotWith3Steps()

        when:
            def result = Project.restore(snapshot).snapshot

        then:
            result.id == snapshot.id
            result.name == snapshot.name
            result.steps.size() == 3
    }

    def "should add step to existing project when it does not contain one"() {
        given:
            def project = Project.restore(ProjectFixture.projectSnapshotWithoutSteps())
            def step = Project.Step.restore(ProjectFixture.projectStepSnapshot())

        when:
            project.addStep(step)

        then:
            with(project.snapshot) {
                it.steps.size() == 1
            }
    }

    def "should not add another step to existing project when it contains this one"() {
        given:
            def project = Project.restore(ProjectFixture.projectSnapshotWithoutSteps())
            def step = Project.Step.restore(ProjectFixture.projectStepSnapshot())

        when:
            project.addStep(step)
            project.addStep(step)

        then:
            with(project.snapshot) {
                it.steps.size() == 1
            }
    }
}
