package io.github.mat3e.project


import io.github.mat3e.task.TaskFacade
import spock.lang.Specification
import spock.lang.Subject

import static java.util.Collections.singletonList

class ProjectFacadeTest extends Specification {

    def factory = new ProjectFactory()
    def repository = new ProjectRepositoryImpl()
    def taskFacade = Mock(TaskFacade)

    @Subject
    def facade = new ProjectFacade(factory, repository, taskFacade)

    def "should update project step to have done corresponding task"() {
        given:
            repository.save(Project.restore(new ProjectSnapshot(10, "20", singletonList(
                    new ProjectStepSnapshot(93, "desc", -2, true, false)
            ))))

        when:
            facade.updateStep(93, true)

        then:
            with(repository.findByNestedStepId(93).get().snapshot) {
                it.steps.stream().filter(step -> step.id == 93)
                    .allMatch(step -> step.correspondingTaskDone)
            }
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