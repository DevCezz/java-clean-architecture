package io.github.mat3e.project


import spock.lang.Specification
import spock.lang.Subject

class ProjectInitializerTest extends Specification {

    def repository = Mock(ProjectRepository)
    def queryRepository = Mock(ProjectQueryRepository)

    @Subject
    def initializer = new ProjectInitializer(repository, queryRepository)
}
