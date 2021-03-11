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
}

class ProjectRepositoryImpl implements ProjectRepository {

    @Override
    Optional<Project> findById(final Integer id) {
        return null
    }

    @Override
    Optional<Project> findByNestedStepId(final Integer id) {
        return null
    }

    @Override
    Project save(final Project entity) {
        return null
    }

    @Override
    void delete(final Project.Step entity) {

    }
}