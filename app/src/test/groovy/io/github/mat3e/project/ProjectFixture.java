package io.github.mat3e.project;

import static java.util.Collections.singletonList;

class ProjectFixture {

    static Project projectWithStepWithId(int id) {
        return Project.restore(
                new ProjectSnapshot(10, "20", singletonList(stepWithId(id).getSnapshot()))
        );
    }

    static Project.Step stepWithId(int id) {
        return Project.Step.restore(
                new ProjectStepSnapshot(id, "desc", -2, true, false)
        );
    }
}
