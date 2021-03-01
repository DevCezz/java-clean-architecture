package io.github.mat3e.project;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ProjectQueryRepository extends Repository<Project, Integer> {

    int count();

    Optional<Project> findById(int id);

    List<Project> findAll();
}
