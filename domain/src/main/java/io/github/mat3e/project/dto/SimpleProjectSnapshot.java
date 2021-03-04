package io.github.mat3e.project.dto;

public class SimpleProjectSnapshot {

    private final int id;
    private final String name;

    SimpleProjectSnapshot(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }
}
