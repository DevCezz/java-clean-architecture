package io.github.mat3e.project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "projects")
public class SqlSimpleProject {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
}