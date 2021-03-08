package io.github.mat3e.project


import spock.lang.Specification

class ProjectTest extends Specification {

    def "should restore project from project snapshot"() {
        given:
            def snapshot = ProjectFixture.projectSnapshotWith3Steps()

        when:
            def result = Project.restore(snapshot).snapshot

        then:
            result.id == snapshot.id
            result.name == snapshot.name
            result.steps.size() == 3
    }
}
