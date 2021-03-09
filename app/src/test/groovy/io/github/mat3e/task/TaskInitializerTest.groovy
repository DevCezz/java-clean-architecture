package io.github.mat3e.task

import spock.lang.Specification
import spock.lang.Subject

class TaskInitializerTest extends Specification {

    def repository = Mock(TaskRepository)
    def queryRepository = Mock(TaskQueryRepository)

    @Subject
    def initializer = new TaskInitializer(repository, queryRepository)
}
