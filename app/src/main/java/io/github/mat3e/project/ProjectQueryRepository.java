package io.github.mat3e.project;

import io.github.mat3e.project.dto.ProjectDto;

import java.util.List;
import java.util.Optional;

public interface ProjectQueryRepository {

    int count();

    Optional<ProjectDto> findDtoById(int id);

    List<ProjectDto> findAllBy();
}
