package io.github.mat3e.task;

import org.springframework.data.repository.Repository;

interface SqlTaskRepository extends TaskRepository, Repository<SqlTask, Integer> {
}

interface SqlTaskQueryRepository extends TaskQueryRepository, Repository<SqlTask, Integer> {
}