package io.github.mat3e.task

import io.github.mat3e.DomainEventPublisher
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
            def tasks = Set.of(
                    new TaskCreator(new TaskSourceId("1"), "desc1", datetime.minusDays(1)),
                    new TaskCreator(new TaskSourceId("2"), "desc2", datetime.minusHours(4))
            )

        when:
            def result = facade.createTasks(tasks)

        then:
            repository.count() == 2
            result.stream().anyMatch(dto -> dto.description == tasks[0].description && dto.deadline == tasks[0].deadline)
            result.stream().anyMatch(dto -> dto.description == tasks[1].description && dto.deadline == tasks[1].deadline)
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
        def id = nextId++
        database.put(id, entity)
        return Task.restore(new TaskSnapshot(id, entity.snapshot))
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