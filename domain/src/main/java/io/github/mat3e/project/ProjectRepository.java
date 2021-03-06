package io.github.mat3e.project;

import java.util.Optional;

interface ProjectRepository {

    Optional<Project> findById(Integer id);

    Optional<Project> findByNestedStepId(Integer id);

    Project save(Project entity);

    void delete(Project.Step entity);
}
