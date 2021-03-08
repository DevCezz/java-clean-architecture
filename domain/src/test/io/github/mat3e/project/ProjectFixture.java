package io.github.mat3e.project;

import java.util.Set;

class ProjectFixture {

    static ProjectSnapshot projectSnapshotWith3Steps() {
        return new ProjectSnapshot(95, "first", projectStepSnapshots());
    }

    private static Set<ProjectStepSnapshot> projectStepSnapshots() {
        return Set.of(
                new ProjectStepSnapshot(1, "1", -1, false, false),
                new ProjectStepSnapshot(2, "2", -2, false, false),
                new ProjectStepSnapshot(3, "3", -3, false, false)
        );
    }
}
