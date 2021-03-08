package io.github.mat3e.project

import spock.lang.Specification

import java.time.ZonedDateTime

import static io.github.mat3e.project.Project.Step
import static io.github.mat3e.project.ProjectFixture.*

class ProjectTest extends Specification {

    def "should restore project from project snapshot"() {
        given:
            def snapshot = projectSnapshotWithStepsWithOneUndoneTask()

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
            def step = Step.restore stepSnapshotId50WithCorrespondingTask(true)

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
            project.addStep(Step.restore(stepSnapshotId50WithCorrespondingTask(true)))
            project.addStep(Step.restore(stepSnapshotId50WithCorrespondingTask(true)))

        then:
            with(project.snapshot) {
                it.steps.size() == 1
            }
    }

    def "should remove step from existing project when it contains this one"() {
        given:
            def project = Project.restore projectSnapshotWithoutSteps()
            project.addStep(Step.restore(stepSnapshotId50WithCorrespondingTask(true)))

        when:
            project.removeStep(Step.restore(stepSnapshotId50WithCorrespondingTask(true)))

        then:
            with(project.snapshot) {
                it.steps.size() == 0
            }
    }

    def "should not remove step from existing project when it does not contain this one"() {
        given:
            def project = Project.restore projectSnapshotWithoutSteps()

        when:
            project.removeStep(Step.restore(stepSnapshotId50WithCorrespondingTask(true)))

        then:
            with(project.snapshot) {
                it.steps.size() == 0
            }
    }

    def "should update info about project"() {
        given:
            def project = Project.restore projectSnapshotWithoutSteps()

        when:
            project.updateInfo("new name")

        then:
            with(project.snapshot) {
                it.name == "new name"
            }
    }

    def "should throw exception when creating new tasks where there are undone tasks"() {
        given:
            def project = Project.restore projectSnapshotWithStepsWithOneUndoneTask()

        when:
            project.convertToTasks(ZonedDateTime.now())

        then:
            def exception = thrown(IllegalStateException)
            exception.message.contains("undone tasks")
    }

    def "should convert steps of project to new tasks"() {
        given:
            def currentDateTime = ZonedDateTime.now()
            def project = Project.restore projectSnapshotWithStepWithDoneTask()

        when:
            def tasks = project.convertToTasks(currentDateTime)

        then:
            with(project.snapshot) {
                tasks.size() == 1
                tasks[0].sourceId.id == String.valueOf(it.steps[0].id)
                tasks[0].description == it.steps[0].description
                tasks[0].deadline.minusDays(it.steps[0].daysToProjectDeadline) == currentDateTime

                it.steps[0].hasCorrespondingTask()
                !it.steps[0].isCorrespondingTaskDone()
            }
    }
}
