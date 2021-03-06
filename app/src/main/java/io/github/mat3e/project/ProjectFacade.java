package io.github.mat3e.project;

import io.github.mat3e.project.dto.ProjectDto;
import io.github.mat3e.project.dto.ProjectStepDto;
import io.github.mat3e.task.TaskFacade;
import io.github.mat3e.task.TaskQueryRepository;
import io.github.mat3e.task.dto.TaskDto;
import io.github.mat3e.task.vo.TaskCreator;
import io.github.mat3e.task.vo.TaskEvent;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class ProjectFacade {
    private final ProjectFactory projectFactory;
    private final ProjectRepository projectRepository;
    private final TaskFacade taskFacade;
    private final TaskQueryRepository taskQueryRepository;

    ProjectFacade(
            final ProjectFactory projectFactory,
            final ProjectRepository projectRepository,
            final TaskFacade taskFacade,
            final TaskQueryRepository taskQueryRepository
    ) {
        this.projectFactory = projectFactory;
        this.projectRepository = projectRepository;
        this.taskFacade = taskFacade;
        this.taskQueryRepository = taskQueryRepository;
    }

    public void handle(TaskEvent event) {
        int stepId = Integer.parseInt(event.getSourceId().getId());
        switch (event.getState()) {
            case DONE:
            case DELETED:
                updateStep(stepId, true);
                break;
            case UNDONE:
                updateStep(stepId, false);
                break;
            case UPDATED:
            default:
                break;
        }
    }

    void updateStep(int stepId, boolean done) {
        projectRepository.findByNestedStepId(stepId)
                .ifPresent(project -> {
                    project.update(stepId, done);
                    projectRepository.save(project);
                });
    }

    public ProjectDto save(ProjectDto dtoToSave) {
        if (dtoToSave.getId() != 0) {
            return toDto(saveWithId(dtoToSave));
        }
        if (dtoToSave.getSteps().stream().anyMatch(step -> step.getId() != 0)) {
            throw new IllegalStateException("Cannot add project with existing steps");
        }
        return toDto(projectRepository.save(projectFactory.from(dtoToSave)));
    }

    private Project saveWithId(ProjectDto dtoToSave) {
        return projectRepository.findById(dtoToSave.getId()).map(existingProject -> {
            ProjectSnapshot toSave = projectFactory.from(dtoToSave).getSnapshot();
            existingProject.updateInfo(toSave.getName());
            Set<Project.Step> removedSteps = existingProject.modifyStepsAs(toSave.getSteps());
            projectRepository.save(existingProject);
            removedSteps.forEach(projectRepository::delete);
            return existingProject;
        }).orElseGet(() -> projectRepository.save(projectFactory.from(dtoToSave)));
    }

    List<TaskDto> createTasks(int projectId, ZonedDateTime projectDeadline) {
        return projectRepository.findById(projectId)
                .map(project -> {
                            Set<TaskCreator> tasks = project.convertToTasks(projectDeadline);
                            return taskFacade.createTasks(tasks);
                        }
                ).orElseThrow(() -> new IllegalArgumentException("No project found with id: " + projectId));
    }

    private ProjectDto toDto(Project project) {
        var snap = project.getSnapshot();
        return ProjectDto.create(snap.getId(), snap.getName(), snap.getSteps().stream().map(this::toDto).collect(toList()));
    }

    private ProjectStepDto toDto(ProjectStepSnapshot step) {
        return ProjectStepDto.create(step.getId(), step.getDescription(), step.getDaysToProjectDeadline());
    }
}