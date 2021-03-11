package io.github.mat3e.project;

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

    static Project projectWithStepDoneTaskWithProjectId(int projectId) {
        return Project.restore(
                new ProjectSnapshot(projectId, "20", singletonList(stepDoneTaskWithStepId(85).getSnapshot()))
        );
    }
}
