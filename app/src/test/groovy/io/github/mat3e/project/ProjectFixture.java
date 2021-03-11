package io.github.mat3e.project;

import io.github.mat3e.project.dto.ProjectDto;
import io.github.mat3e.project.dto.ProjectStepDto;

import static java.util.Collections.singletonList;

class ProjectFixture {

    static Project projectWithStepUndoneTaskWithStepId(int stepId) {
        return Project.restore(
                new ProjectSnapshot(10, "20", singletonList(stepUndoneTaskWithStepId(stepId).getSnapshot()))
        );
    }

    static Project.Step stepUndoneTaskWithStepId(int stepId) {
        return Project.Step.restore(
                new ProjectStepSnapshot(stepId, "desc", -2, true, false)
        );
    }


    static Project projectWithStepDoneTaskWithStepId(int stepId) {
        return Project.restore(
                new ProjectSnapshot(10, "20", singletonList(stepDoneTaskWithStepId(stepId).getSnapshot()))
        );
    }

    static Project.Step stepDoneTaskWithStepId(int stepId) {
        return Project.Step.restore(
                new ProjectStepSnapshot(stepId, "desc", -2, true, true)
        );
    }

    static ProjectDto projectDtoWithStepDoneTaskOfProjectId(int projectId) {
        return ProjectDto.create(
                projectId, "new project", singletonList(stepDtoDoneTaskOfStepId(85))
        );
    }

    static ProjectStepDto stepDtoDoneTaskOfStepId(int stepId) {
        return ProjectStepDto.create(
                stepId, "new step", -10
        );
    }
}
