package io.github.mat3e.project.query;

import java.util.List;

interface ProjectDto {

    int getId();

    String getName();

    List<ProjectStepDto> getSteps();
}
