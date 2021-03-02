package io.github.mat3e.project.dto;

import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "projects")
public class SimpleProjectQueryEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;

    @PersistenceConstructor
    protected SimpleProjectQueryEntity() {}

    public SimpleProjectQueryEntity(final int id, final String name) {
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