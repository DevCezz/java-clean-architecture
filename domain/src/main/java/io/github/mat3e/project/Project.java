package io.github.mat3e.project;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class Project {

    static Project restore(ProjectSnapshot snapshot) {
        return new Project(
                snapshot.getId(),
                snapshot.getName(),
                snapshot.getSteps().stream()
                        .map(Step::restore)
                        .collect(Collectors.toSet())
        );
    }

    private int id;
    private String name;
    private final Set<Step> steps = new HashSet<>();

    private Project(final int id, final String name, final Set<Step> steps) {
        this.id = id;
        this.name = name;
        steps.forEach(this::addStep);
    }

    void addStep(Step step) {
        if (steps.contains(step)) {
            return;
        }
        steps.add(step);
    }

    void removeStep(Step step) {
        if (!steps.contains(step)) {
            return;
        }
        steps.remove(step);
    }

    ProjectSnapshot getSnapshot() {
        return new ProjectSnapshot(id, name, steps.stream().map(Step::getSnapshot).collect(Collectors.toSet()));
    }

    static class Step {

        static Step restore(ProjectStepSnapshot snapshot) {
            return new Step(snapshot.getId(), snapshot.getDescription(), snapshot.getDaysToProjectDeadline());
        }

        private int id;
        private String description;
        private int daysToProjectDeadline;

        private Step(final int id, final String description, final int daysToProjectDeadline) {
            this.id = id;
            this.description = description;
            this.daysToProjectDeadline = daysToProjectDeadline;
        }

        ProjectStepSnapshot getSnapshot() {
            return new ProjectStepSnapshot(id, description, daysToProjectDeadline);
        }
    }
}
