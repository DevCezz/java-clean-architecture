package io.github.mat3e.project.dto;

public class SimpleProject {

    public static SimpleProject restore(SimpleProjectSnapshot snapshot) {
        return new SimpleProject(snapshot.getId(), snapshot.getName());
    }

    private final int id;
    private final String name;

    public SimpleProject(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}