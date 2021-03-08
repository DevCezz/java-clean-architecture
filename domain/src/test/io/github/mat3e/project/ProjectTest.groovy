package io.github.mat3e.project

import spock.lang.Specification

import java.time.ZonedDateTime

import static io.github.mat3e.project.Project.Step
import static io.github.mat3e.project.ProjectFixture.*

class ProjectTest extends Specification {

    def "should restore project from project snapshot"() {
        given:
            def snapshot = projectWithStepsWhereOneTaskIsUndone()

        when:
            def result = Project.restore snapshot getSnapshot()

        then:
            result.id == snapshot.id
            result.name == snapshot.name
            result.steps.size() == 3
    }

    def "should add step to existing project when it does not contain one"() {
        given:
            def project = Project.restore projectWithoutSteps()
            def step = Step.restore stepWithDoneCorrespondingTask(43)

        when:
            project.addStep(step)

        then:
            with(project.snapshot) {
                it.steps.size() == 1
            }
    }

    def "should not add another step to existing project when it contains this one"() {
        given:
            def project = Project.restore projectWithoutSteps()

        when:
            project.addStep(Step.restore(stepWithDoneCorrespondingTask(12)))
            project.addStep(Step.restore(stepWithDoneCorrespondingTask(12)))

        then:
            with(project.snapshot) {
                it.steps.size() == 1
            }
    }

    def "should remove step from existing project when it contains this one"() {
        given:
            def project = Project.restore projectWithoutSteps()
            project.addStep(Step.restore(stepWithDoneCorrespondingTask(12)))

        when:
            project.removeStep(Step.restore(stepWithDoneCorrespondingTask(12)))

        then:
            with(project.snapshot) {
                it.steps.size() == 0
            }
    }

    def "should not remove step from existing project when it does not contain this one"() {
        given:
            def project = Project.restore projectWithoutSteps()

        when:
            project.removeStep(Step.restore(stepWithDoneCorrespondingTask(12)))

        then:
            with(project.snapshot) {
                it.steps.size() == 0
            }
    }

    def "should update info about project"() {
        given:
            def project = Project.restore projectWithoutSteps()

        when:
            project.updateInfo("new name")

        then:
            with(project.snapshot) {
                it.name == "new name"
            }
    }

    def "should throw exception when creating new tasks where there are undone tasks"() {
        given:
            def project = Project.restore projectWithStepsWhereOneTaskIsUndone()

        when:
            project.convertToTasks(ZonedDateTime.now())

        then:
            def exception = thrown(IllegalStateException)
            exception.message.contains("undone tasks")
    }

    def "should convert steps of project to new tasks"() {
        given:
            def currentDateTime = ZonedDateTime.now()
            def project = Project.restore projectWithStepDoneTask()

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

    def "should update step with undone task to be done"() {
        given:
            def project = Project.restore projectWithStepUndoneTask(102)

        when:
            project.update(102, true)

        then:
            with(project.snapshot) {
                it.steps[0].correspondingTaskDone
            }
    }

    def "should not update step with undone task to be done where step id is wrong"() {
        given:
            def project = Project.restore projectWithStepUndoneTask(102)

        when:
            project.update(104, true)

        then:
            with(project.snapshot) {
                !it.steps[0].correspondingTaskDone
            }
    }
}
