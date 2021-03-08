package io.github.mat3e.task


import spock.lang.Specification

class TaskTest extends Specification {

    def "should restore task from task snapshot"() {
        given:
            def snapshot = TaskFixture.doneTaskSnapshot()

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

    def "should create task from task creator"() {
        given:
            def creator = TaskFixture.taskCreator()

        when:
            def result = Task.createFrom(creator).getSnapshot()

        then:
            result.description == creator.description
            result.deadline == creator.deadline
            result.sourceId.id == creator.sourceId.id
    }

    def "should toggle task to invert done and increase counter"() {
        given:
            def undoneTask = TaskFixture.undoneTaskSnapshot()
            def task = Task.restore undoneTask

        when:
            task.toggle()

        then:
            with(task.snapshot) {
                it.changesCount == undoneTask.changesCount + 1
                it.done == !undoneTask.done
            }
    }
}
