package io.github.mat3e.task;

import org.springframework.data.jpa.repository.JpaRepository;

interface TaskRepository extends JpaRepository<Task, Integer> {
    boolean existsByDoneIsFalseAndProject_Id(int id);
}
