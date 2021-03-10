package io.github.mat3e.project;

import io.github.mat3e.project.dto.ProjectDto;

import static java.util.stream.Collectors.toList;

class ProjectFactory {
    Project from(ProjectDto source) {
        var result = new ProjectSnapshot(
                source.getId(),
                source.getName(),
                source.getSteps().stream().map(step ->
                        new ProjectStepSnapshot(
                                step.getId(),
                                step.getDescription(),
                                step.getDaysToProjectDeadline(),
                                false,
                                false
                        )
                ).collect(toList()));

        return Project.restore(result);
    }
}
