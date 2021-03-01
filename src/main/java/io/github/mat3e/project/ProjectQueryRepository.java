package io.github.mat3e.project;

import io.github.mat3e.project.query.ProjectDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ProjectQueryRepository extends Repository<Project, Integer> {

    int count();

    Optional<ProjectDto> findById(int id);

    List<ProjectDto> findAll();
}
