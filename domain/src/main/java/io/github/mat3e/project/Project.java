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
                        .map(ProjectStep::restore)
                        .collect(Collectors.toSet())
        );
    }

    private int id;
    private String name;
    private final Set<ProjectStep> steps = new HashSet<>();

    private Project(final int id, final String name, final Set<ProjectStep> steps) {
        this.id = id;
        this.name = name;
        steps.forEach(this::addStep);
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public Set<ProjectStep> getSteps() {
        return steps;
    }

    void addStep(ProjectStep step) {
        if (steps.contains(step)) {
            return;
        }
        steps.add(step);
        step.setProject(this);
    }

    void removeStep(ProjectStep step) {
        if (!steps.contains(step)) {
            return;
        }
        steps.remove(step);
        step.setProject(null);
    }
}
