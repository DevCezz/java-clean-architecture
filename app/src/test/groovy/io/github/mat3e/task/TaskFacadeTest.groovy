package io.github.mat3e.task

import io.github.mat3e.DomainEventPublisher
import io.github.mat3e.task.dto.TaskDto
import io.github.mat3e.task.vo.TaskCreator
import io.github.mat3e.task.vo.TaskEvent
import io.github.mat3e.task.vo.TaskSourceId
import org.junit.jupiter.api.BeforeEach
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.stream.Collectors
import java.util.stream.StreamSupport

class TaskFacadeTest extends Specification {

    def factory = new TaskFactory()
    def repository = new TaskRepositoryImpl()
    def publisher = Mock(DomainEventPublisher)

    @Subject
    def facade = new TaskFacade(factory, repository, publisher)

    @BeforeEach
    void clearDatabase() {
        repository.clear();
    }

    def "should return task dtos when tasks are created"() {
        given:
            def datetime = ZonedDateTime.of(
                    LocalDate.of(2020, 02, 02),
                    LocalTime.of(14, 45),
                    ZoneId.of("Europe/Warsaw")
            )
            def task = new TaskCreator(new TaskSourceId("1"), "desc1", datetime)

        when:
            def result = facade.createTasks(Collections.singleton(task))

        then:
            repository.count() == 1
            with(result.find()) {
                it.description == task.description
                it.deadline == task.deadline
            }
    }

    def "should save task in repository when there is no such task"() {
        given:
            def deadline = ZonedDateTime.of(
                    LocalDate.of(2020, 02, 02),
                    LocalTime.of(14, 45),
                    ZoneId.of("Europe/Warsaw")
            )
            def task = new TaskDto(14, "desc", true, deadline, "foo")

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

    def "should update task in repository when there is such task"() {
        given:
            def deadline = ZonedDateTime.of(
                    LocalDate.of(2020, 02, 02),
                    LocalTime.of(14, 45),
                    ZoneId.of("Europe/Warsaw")
            )
            def saved = repository.save(Task.restore(
                    new TaskSnapshot(0, "desc", true, deadline.minusDays(4), 10, "foo", new TaskSourceId("97"))
            )).getSnapshot()
            def task = new TaskDto(saved.id, "desc-new", true, deadline, "bar")

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
            def deadline = ZonedDateTime.of(
                    LocalDate.of(2020, 02, 02),
                    LocalTime.of(14, 45),
                    ZoneId.of("Europe/Warsaw")
            )
            def saved = repository.save(Task.restore(
                    new TaskSnapshot(0, "desc", true, deadline.minusDays(4), 10, "foo", new TaskSourceId("97"))
            )).getSnapshot()
            def task = new TaskDto(saved.id, "desc-new", false, deadline, "bar")

        when:
            facade.save(task)

        then:
            0 * publisher.publish({ it.state == TaskEvent.State.DONE })
            1 * publisher.publish({ it.state == TaskEvent.State.UNDONE })
            1 * publisher.publish({ it.state == TaskEvent.State.UPDATED })
    }

    def "should publish events when task is updated to be done"() {
        given:
            def deadline = ZonedDateTime.of(
                    LocalDate.of(2020, 02, 02),
                    LocalTime.of(14, 45),
                    ZoneId.of("Europe/Warsaw")
            )
            def saved = repository.save(Task.restore(
                    new TaskSnapshot(0, "desc", false, deadline.minusDays(4), 10, "foo", new TaskSourceId("97"))
            )).getSnapshot()
            def task = new TaskDto(saved.id, "desc-new", true, deadline, "bar")

        when:
            facade.save(task)

        then:
            0 * publisher.publish({ it.state == TaskEvent.State.UNDONE })
            1 * publisher.publish({ it.state == TaskEvent.State.DONE })
            1 * publisher.publish({ it.state == TaskEvent.State.UPDATED })
    }

    def "should delete task when there is such task in repository"() {
        given:
            def deadline = ZonedDateTime.of(
                    LocalDate.of(2020, 02, 02),
                    LocalTime.of(14, 45),
                    ZoneId.of("Europe/Warsaw")
            )
            def saved = repository.save(Task.restore(
                    new TaskSnapshot(0, "desc", false, deadline.minusDays(4), 10, "foo", new TaskSourceId("97"))
            )).getSnapshot()

        when:
            facade.delete(saved.id)

        then:
            repository.count() == 0
    }

    def "should publish event when task is deleted from repository"() {
        given:
            def deadline = ZonedDateTime.of(
                    LocalDate.of(2020, 02, 02),
                    LocalTime.of(14, 45),
                    ZoneId.of("Europe/Warsaw")
            )
            def saved = repository.save(Task.restore(
                    new TaskSnapshot(0, "desc", false, deadline.minusDays(4), 10, "foo", new TaskSourceId("97"))
            )).getSnapshot()

        when:
            facade.delete(saved.id)

        then:
            1 * publisher.publish({ it.state == TaskEvent.State.DELETED })
    }
}

class TaskRepositoryImpl implements TaskRepository {

    private int nextId = 1;
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