package io.github.mat3e.project;

import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "projects")
class SqlProject {

    static SqlProject from(Project source) {
        var result = new SqlProject();
        result.id = source.getId();
        result.name = source.getName();
        result.steps.addAll(source.getSteps().stream()
                .map(step -> SqlProjectStep.from(step, result))
                .collect(Collectors.toSet())
        );
        return result;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.EAGER)
    private final Set<SqlProjectStep> steps = new HashSet<>();

    @PersistenceConstructor
    public SqlProject() {
    }

    Project toProject() {
        var result = new Project();
        result.setId(id);
        result.setName(name);
        steps.stream().map(step -> step.toStep(result)).forEach(result::addStep);
        return result;
    }
}
