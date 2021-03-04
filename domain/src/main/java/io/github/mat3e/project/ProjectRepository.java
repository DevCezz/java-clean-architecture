package io.github.mat3e.project;

import java.util.Optional;

interface ProjectRepository {

    Optional<Project> findById(Integer id);

    Project save(Project entity);
}
