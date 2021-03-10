package io.github.mat3e.task

import io.github.mat3e.DomainEventPublisher
import io.github.mat3e.task.dto.TaskDto
import io.github.mat3e.task.vo.TaskCreator
import io.github.mat3e.task.vo.TaskSourceId
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
}