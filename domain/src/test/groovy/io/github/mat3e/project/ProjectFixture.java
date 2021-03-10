package io.github.mat3e.project;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

class ProjectFixture {

    static ProjectSnapshot projectWithStepsWhereOneTaskIsUndone() {
        return new ProjectSnapshot(95, "first", stepsWhereOneTaskIsUndone());
    }

    private static List<ProjectStepSnapshot> stepsWhereOneTaskIsUndone() {
        return List.of(
                stepWithDoneCorrespondingTask(1),
                stepWithUndoneCorrespondingTask(2),
                stepWithDoneCorrespondingTask(3)
        );
    }

    static ProjectSnapshot projectWithStepDoneTask() {
        return new ProjectSnapshot(95, "first", singletonList(stepWithDoneCorrespondingTask(50)));
    }

    static ProjectSnapshot projectWithStepUndoneTask(int stepId) {
        return new ProjectSnapshot(95, "first", singletonList(stepWithUndoneCorrespondingTask(stepId)));
    }

    static ProjectSnapshot projectWithoutSteps() {
        return new ProjectSnapshot(95, "first", emptyList());
    }

    static ProjectStepSnapshot stepWithDoneCorrespondingTask(int id) {
        return new ProjectStepSnapshot(id, "another", -3, true, true);
    }

    static ProjectStepSnapshot stepWithUndoneCorrespondingTask(int id) {
        return new ProjectStepSnapshot(id, "step", -3, true, false);
    }
}
