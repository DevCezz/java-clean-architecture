package io.github.mat3e.project.dto;

public class SimpleProjectSnapshot {

    private int id;
    private String name;

    SimpleProjectSnapshot() {}

    public SimpleProjectSnapshot(final int id, final String name) {
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
