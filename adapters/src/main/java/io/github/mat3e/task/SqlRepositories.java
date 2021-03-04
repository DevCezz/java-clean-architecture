package io.github.mat3e.task;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

interface SqlTaskRepository extends Repository<SqlTask, Integer> {

    Optional<SqlTask> findById(Integer id);

    SqlTask save(SqlTask entity);

    List<SqlTask> saveAll(Iterable<SqlTask> entities);

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
        return sqlTaskRepository.findById(id).map(SqlTask::toTask);
    }

    @Override
    public Task save(final Task entity) {
        return sqlTaskRepository.save(SqlTask.from(entity)).toTask();
    }

    @Override
    public List<Task> saveAll(final Iterable<Task> entities) {
        return sqlTaskRepository.saveAll(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(SqlTask::from)
                        .collect(Collectors.toList())
        ).stream()
                .map(SqlTask::toTask)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(final Integer id) {
        sqlTaskRepository.deleteById(id);
    }
}

interface SqlTaskQueryRepository extends TaskQueryRepository, Repository<SqlTask, Integer> {
}