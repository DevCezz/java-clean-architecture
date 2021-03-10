package io.github.mat3e.project;

import java.util.ArrayList;
import java.util.List;

class ProjectSnapshot {

    private int id;
    private String name;
    private final List<ProjectStepSnapshot> steps = new ArrayList<>();

    ProjectSnapshot() {}

    ProjectSnapshot(final int id, final String name, final List<ProjectStepSnapshot> steps) {
        this.id = id;
        this.name = name;
        this.steps.addAll(steps);
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    List<ProjectStepSnapshot> getSteps() {
        return steps;
    }
}
