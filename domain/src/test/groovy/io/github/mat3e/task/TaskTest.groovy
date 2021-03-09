package io.github.mat3e.task

import io.github.mat3e.task.vo.TaskEvent
import spock.lang.Specification
import spock.lang.Unroll

import java.time.ZonedDateTime

import static io.github.mat3e.task.Task.createFrom
import static io.github.mat3e.task.Task.restore
import static io.github.mat3e.task.TaskFixture.doneTaskSnapshot
import static io.github.mat3e.task.TaskFixture.undoneTaskSnapshot

class TaskTest extends Specification {

    def "should restore task from task snapshot"() {
        given:
            def snapshot = doneTaskSnapshot()

        when:
            def result = restore snapshot getSnapshot()

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
            def result = createFrom creator getSnapshot()

        then:
            result.description == creator.description
            result.deadline == creator.deadline
            result.sourceId.id == creator.sourceId.id
    }

    def "should toggle task to invert done and increase counter"() {
        given:
            def undoneTask = undoneTaskSnapshot()
            def task = restore undoneTask

        when:
            task.toggle()

        then:
            with(task.snapshot) {
                it.changesCount == undoneTask.changesCount + 1
                it.done == !undoneTask.done
            }
    }

    @Unroll
    def "should event has state #result when task done set to #source.done before toggle"() {
        given:
            def task = restore source

        when:
            def event = task.toggle()

        then:
            with(task.snapshot) {
                event.sourceId == it.sourceId
                event.state == result
                event.data == null
            }

        where:
            source                  | result
            undoneTaskSnapshot()    | TaskEvent.State.DONE
            doneTaskSnapshot()      | TaskEvent.State.UNDONE
    }

    def "should update info about task"() {
        given:
            def now = ZonedDateTime.now();
            def task = restore undoneTaskSnapshot()

        when:
            task.updateInfo("new desc", now, "new additional comment")

        then:
            with(task.snapshot) {
                it.description == "new desc"
                it.deadline == now
                it.additionalComment == "new additional comment"
            }
    }
}
