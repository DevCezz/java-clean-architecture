package io.github.mat3e.project;

import static java.util.Collections.singletonList;

class ProjectFixture {

    static Project projectWithStepUndoneTaskWithId(int id) {
        return Project.restore(
                new ProjectSnapshot(10, "20", singletonList(stepUndoneTaskWithId(id).getSnapshot()))
        );
    }

    static Project.Step stepUndoneTaskWithId(int id) {
        return Project.Step.restore(
                new ProjectStepSnapshot(id, "desc", -2, true, false)
        );
    }


    static Project projectWithStepDoneTaskWithId(int id) {
        return Project.restore(
                new ProjectSnapshot(10, "20", singletonList(stepDoneTaskWithId(id).getSnapshot()))
        );
    }

    static Project.Step stepDoneTaskWithId(int id) {
        return Project.Step.restore(
                new ProjectStepSnapshot(id, "desc", -2, true, true)
        );
    }

    static Project projectWithStepDoneTask(int projectId) {
        return Project.restore(
                new ProjectSnapshot(projectId, "20", singletonList(stepDoneTaskWithId(85).getSnapshot()))
        );
    }
}
