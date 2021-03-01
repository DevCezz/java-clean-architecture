package io.github.mat3e.project.query;

import java.util.List;

public interface ProjectDto {

    int getId();

    String getName();

    List<ProjectStepDto> getSteps();
}
