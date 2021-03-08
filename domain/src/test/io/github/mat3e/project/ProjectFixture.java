package io.github.mat3e.project;

import java.util.Collections;
import java.util.Set;

class ProjectFixture {

    static ProjectSnapshot projectSnapshotWithStepsWithOneUndoneTask() {
        return new ProjectSnapshot(95, "first", stepSnapshotsWithOneUndoneTask());
    }

    private static Set<ProjectStepSnapshot> stepSnapshotsWithOneUndoneTask() {
        return Set.of(
                new ProjectStepSnapshot(1, "1", -1, true, true),
                new ProjectStepSnapshot(2, "2", -2, true, false),
                new ProjectStepSnapshot(3, "3", -3, true, true)
        );
    }

    static ProjectSnapshot projectSnapshotWithStepWithDoneTask() {
        return new ProjectSnapshot(95, "first", Collections.singleton(stepSnapshotId50WithCorrespondingTask(true)));
    }

    static ProjectSnapshot projectSnapshotWithoutSteps() {
        return new ProjectSnapshot(95, "first", Collections.emptySet());
    }

    static ProjectStepSnapshot stepSnapshotId50WithCorrespondingTask(boolean taskIsDone) {
        return new ProjectStepSnapshot(50, "another", -3, true, taskIsDone);
    }
}
