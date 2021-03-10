package io.github.mat3e.task

import io.github.mat3e.DomainEventPublisher
import io.github.mat3e.task.dto.TaskDto
import io.github.mat3e.task.vo.TaskCreator
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