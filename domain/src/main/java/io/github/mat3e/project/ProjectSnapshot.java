package io.github.mat3e.project;

import java.util.HashSet;
import java.util.Set;

class ProjectSnapshot {

    private final int id;
    private final String name;
    private final Set<ProjectStepSnapshot> steps = new HashSet<>();

    ProjectSnapshot(final int id, final String name, final Set<ProjectStepSnapshot> steps) {
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

    Set<ProjectStepSnapshot> getSteps() {
        return steps;
    }
}