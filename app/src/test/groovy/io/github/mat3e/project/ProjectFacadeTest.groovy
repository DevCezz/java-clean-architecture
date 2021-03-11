package io.github.mat3e.project


import io.github.mat3e.task.TaskFacade
import spock.lang.Specification
import spock.lang.Subject

class ProjectFacadeTest extends Specification {

    def factory = new ProjectFactory()
    def repository = new ProjectRepositoryImpl()
    def taskFacade = Mock(TaskFacade)

    @Subject
    def facade = new ProjectFacade(factory, repository, taskFacade)

    def "should not save project when not find one while updating step"() {
        when:
            facade.updateStep(10, true)

        then:
            0 * repository.save(_)
    }
}

class ProjectRepositoryImpl implements ProjectRepository {

    private int nextId = 1
    private Map<Integer, Project> database = new HashMap<>()

    @Override
    Optional<Project> findById(final Integer id) {
        return Optional.ofNullable(database.get(id))
    }

    @Override
    Optional<Project> findByNestedStepId(final Integer id) {
        return database.values().stream()
            .filter(project -> project.snapshot.steps.stream()
                    .anyMatch(step -> step.id == id)
            ).findFirst()
    }

    @Override
    Project save(final Project entity) {
        def project = findById(entity.snapshot.id)
                .map(task -> entity)
                .orElseGet(() -> Project.restore(new ProjectSnapshot(nextId++, entity.snapshot)))
        database.put(project.snapshot.id, project)
        return project
    }

    @Override
    void delete(final Project.Step entity) {
        findByNestedStepId(entity.snapshot.id)
            .ifPresent(project -> project.removeStep(entity.snapshot.id))
    }
}