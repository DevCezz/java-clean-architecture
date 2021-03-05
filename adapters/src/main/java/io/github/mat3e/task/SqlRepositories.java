package io.github.mat3e.task;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

interface SqlTaskRepository extends Repository<TaskSnapshot, Integer> {

    Optional<TaskSnapshot> findById(Integer id);

    TaskSnapshot save(TaskSnapshot entity);

    List<TaskSnapshot> saveAll(Iterable<TaskSnapshot> entities);

    void deleteById(Integer id);
}

@org.springframework.stereotype.Repository
class TaskRepositoryImpl implements TaskRepository {

    private final SqlTaskRepository sqlTaskRepository;

    TaskRepositoryImpl(final SqlTaskRepository sqlTaskRepository) {
        this.sqlTaskRepository = sqlTaskRepository;
    }

    @Override
    public Optional<Task> findById(final Integer id) {
        return sqlTaskRepository.findById(id).map(Task::restore);
    }

    @Override
    public Task save(final Task entity) {
        return Task.restore(sqlTaskRepository.save(entity.getSnapshot()));
    }

    @Override
    public List<Task> saveAll(final Iterable<Task> entities) {
        return sqlTaskRepository.saveAll(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(Task::getSnapshot)
                        .collect(Collectors.toList())
        ).stream()
                .map(Task::restore)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(final Integer id) {
        sqlTaskRepository.deleteById(id);
    }
}

interface SqlTaskQueryRepository extends TaskQueryRepository, Repository<TaskSnapshot, Integer> {
}