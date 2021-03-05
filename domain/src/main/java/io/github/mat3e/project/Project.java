package io.github.mat3e.project;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

class Project {

    static Project restore(ProjectSnapshot snapshot) {
        return new Project(
                snapshot.getId(),
                snapshot.getName(),
                snapshot.getSteps().stream()
                        .map(Step::restore)
                        .collect(toSet())
        );
    }

    private final int id;
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
        return new ProjectSnapshot(id, name, steps.stream().map(Step::getSnapshot).collect(toSet()));
    }

    Set<Step> modifyStepsAs(final Set<ProjectStepSnapshot> stepSnapshots) {
        Set<Step> stepsToRemove = new HashSet<>();
        steps.forEach(existingStep -> stepSnapshots.stream()
                .map(Step::restore)
                .filter(existingStep::equals)
                .findFirst()
                .ifPresentOrElse(
                        overridingStep -> existingStep.updateInfo(
                                overridingStep.description,
                                overridingStep.daysToProjectDeadline
                        ),
                        () -> stepsToRemove.add(existingStep)
                )
        );
        stepsToRemove.forEach(this::removeStep);
        stepSnapshots.stream()
                .map(Step::restore)
                .filter(newStep -> steps.stream()
                        .noneMatch(existingStep -> existingStep.equals(newStep))
                )
                .collect(toSet())
                // collecting first to allow multiple id=0
                .forEach(this::addStep);

        return stepsToRemove;
    }

    void updateInfo(String name) {
        this.name = name;
    }

    static class Step {

        static Step restore(ProjectStepSnapshot snapshot) {
            return new Step(snapshot.getId(), snapshot.getDescription(), snapshot.getDaysToProjectDeadline());
        }

        private final int id;
        private String description;
        private int daysToProjectDeadline;

        private Step(final int id, final String description, final int daysToProjectDeadline) {
            this.id = id;
            this.description = description;
            this.daysToProjectDeadline = daysToProjectDeadline;
        }

        void updateInfo(String description, int daysToProjectDeadline) {
            this.description = description;
            this.daysToProjectDeadline = daysToProjectDeadline;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Step step = (Step) o;
            return id == step.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        ProjectStepSnapshot getSnapshot() {
            return new ProjectStepSnapshot(id, description, daysToProjectDeadline);
        }
    }
}
