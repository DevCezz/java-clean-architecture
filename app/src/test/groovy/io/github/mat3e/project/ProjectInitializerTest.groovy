package io.github.mat3e.project

import spock.lang.Specification
import spock.lang.Subject

class ProjectInitializerTest extends Specification {

    def repository = Mock(ProjectRepository)
    def queryRepository = Mock(ProjectQueryRepository)

    @Subject
    def initializer = new ProjectInitializer(repository, queryRepository)

    def "should invoke save for project with 3 steps when there is no project in repo"() {
        given:
            queryRepository.count() >> 0

        when:
            initializer.init()

        then:
            1 * repository.save({ it.steps.size() == 3 })
    }
}
