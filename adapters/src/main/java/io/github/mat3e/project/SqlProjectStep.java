package io.github.mat3e.project;

import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "project_steps")
class SqlProjectStep {

    static SqlProjectStep from(ProjectStep source) {
        var result = new SqlProjectStep();
        result.id = source.getId();
        result.description = source.getDescription();
        result.daysToProjectDeadline = source.getDaysToProjectDeadline();
        result.project = source.getProject() == null ? null : SqlProject.from(source.getProject());
        return result;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    @NotNull
    private String description;
    private int daysToProjectDeadline;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private SqlProject project;

    @PersistenceConstructor
    public SqlProjectStep() {
    }

    ProjectStep toProjectStep() {
        var result = new ProjectStep(description, daysToProjectDeadline, project == null ? null : project.toProject());
        result.setId(id);
        return result;
    }
}
