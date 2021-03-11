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
}
