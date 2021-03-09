package io.github.mat3e.project;

import java.util.Collections;
import java.util.Set;

class ProjectFixture {

    static ProjectSnapshot projectWithStepsWhereOneTaskIsUndone() {
        return new ProjectSnapshot(95, "first", stepsWhereOneTaskIsUndone());
    }

    private static Set<ProjectStepSnapshot> stepsWhereOneTaskIsUndone() {
        return Set.of(
                stepWithDoneCorrespondingTask(1),
                stepWithUndoneCorrespondingTask(2),
                stepWithDoneCorrespondingTask(3)
        );
    }

    static ProjectSnapshot projectWithStepDoneTask() {
        return new ProjectSnapshot(95, "first", Collections.singleton(stepWithDoneCorrespondingTask(50)));
    }

    static ProjectSnapshot projectWithStepUndoneTask(int stepId) {
        return new ProjectSnapshot(95, "first", Collections.singleton(stepWithUndoneCorrespondingTask(stepId)));
    }

    static ProjectSnapshot projectWithoutSteps() {
        return new ProjectSnapshot(95, "first", Collections.emptySet());
    }

    static ProjectStepSnapshot stepWithDoneCorrespondingTask(int id) {
        return new ProjectStepSnapshot(id, "another", -3, true, true);
    }

    static ProjectStepSnapshot stepWithUndoneCorrespondingTask(int id) {
        return new ProjectStepSnapshot(id, "step", -3, true, false);
    }
}
