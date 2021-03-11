package io.github.mat3e.task

import io.github.mat3e.DomainEventPublisher
import io.github.mat3e.task.vo.TaskEvent
import org.junit.jupiter.api.BeforeEach
import spock.lang.Specification
import spock.lang.Subject

import java.util.stream.Collectors
import java.util.stream.StreamSupport

import static io.github.mat3e.task.TaskFixture.DATETIME
import static io.github.mat3e.task.TaskFixture.taskCreator
import static io.github.mat3e.task.TaskFixture.taskDto
import static io.github.mat3e.task.TaskFixture.doneTask
import static io.github.mat3e.task.TaskFixture.doneTaskDtoWithId
import static io.github.mat3e.task.TaskFixture.undoneTask
import static io.github.mat3e.task.TaskFixture.undoneTaskDtoWithId

class TaskFacadeTest extends Specification {

    def factory = new TaskFactory()
    def repository = new TaskRepositoryImpl()
    def publisher = Mock(DomainEventPublisher)

    @Subject
    def facade = new TaskFacade(factory, repository, publisher)

    @BeforeEach
    void clearDatabase() {
        repository.clear()
    }

    def "should return task dto list when tasks are created"() {
        given:
            def taskCreator = taskCreator()

        when:
            def result = facade.createTasks Collections.singleton(taskCreator)

        then:
            repository.count() == 1
            with(result.find()) {
                it.description == taskCreator.description
                it.deadline == taskCreator.deadline
            }
    }

    def "should save task in repository when there is no such task"() {
        given:
            def dto = taskDto()

        when:
            def result = facade.save dto

        then:
            repository.count() == 1
            with(result) {
                it.description == dto.description
                it.done == dto.done
                it.deadline == dto.deadline
                it.additionalComment == dto.additionalComment
            }
    }

    def "should update task in repository when there is such task"() {
        given:
            def saved = repository.save doneTask("desc", DATETIME.minusDays(4), "foo")
            def task = doneTaskDtoWithId(saved.snapshot.id, "desc-new", DATETIME, "bar")

        when:
            def result = facade.save(task)

        then:
            repository.count() == 1
            with(result) {
                it.description == task.description
                it.done == task.done
                it.deadline == task.deadline
                it.additionalComment == task.additionalComment
            }
    }

    def "should publish events when task is updated to be undone"() {
        given:
            def saved = repository.save(doneTask("desc", DATETIME.minusDays(4), "foo"))
            def task = undoneTaskDtoWithId(saved.snapshot.id, "desc-new", DATETIME, "bar")

        when:
            facade.save(task)

        then:
            0 * publisher.publish({ it.state == TaskEvent.State.DONE })
            1 * publisher.publish({ it.state == TaskEvent.State.UNDONE })
            1 * publisher.publish({ it.state == TaskEvent.State.UPDATED })
    }

    def "should publish events when task is updated to be done"() {
        given:
            def saved = repository.save(undoneTask("desc", DATETIME.minusDays(4), "foo"))
            def task = doneTaskDtoWithId(saved.snapshot.id, "desc-new", DATETIME, "bar")

        when:
            facade.save(task)

        then:
            0 * publisher.publish({ it.state == TaskEvent.State.UNDONE })
            1 * publisher.publish({ it.state == TaskEvent.State.DONE })
            1 * publisher.publish({ it.state == TaskEvent.State.UPDATED })
    }

    def "should delete task when there is such task in repository"() {
        given:
            def saved = repository.save(doneTask("desc", DATETIME, "foo"))

        when:
            facade.delete(saved.snapshot.id)

        then:
            repository.count() == 0
    }

    def "should publish event when task is deleted from repository"() {
        given:
            def saved = repository.save(doneTask("desc", DATETIME, "foo"))

        when:
            facade.delete(saved.snapshot.id)

        then:
            1 * publisher.publish({ it.state == TaskEvent.State.DELETED })
    }
}

class TaskRepositoryImpl implements TaskRepository {

    private int nextId = 1
    private Map<Integer, Task> database = new HashMap<>()

    @Override
    Optional<Task> findById(final Integer id) {
        return Optional.ofNullable(database.get(id))
    }

    @Override
    Task save(final Task entity) {
        def task = findById(entity.snapshot.id)
                .map(task -> entity)
                .orElseGet(() -> Task.restore(new TaskSnapshot(nextId++, entity.snapshot)))
        database.put(task.snapshot.id, task)
        return task
    }

    @Override
    List<Task> saveAll(final Iterable<Task> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
            .map(this::save)
            .collect(Collectors.toList())
    }

    @Override
    void deleteById(final Integer id) {
        database.remove(id)
    }

    int count() {
        return database.size()
    }

    void clear() {
        database.clear()
    }
}