package io.github.mat3e.project

import io.github.mat3e.project.dto.ProjectDto
import io.github.mat3e.project.dto.ProjectStepDto
import io.github.mat3e.task.TaskFacade
import io.github.mat3e.task.vo.TaskEvent
import io.github.mat3e.task.vo.TaskSourceId
import org.junit.jupiter.api.BeforeEach
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.ZonedDateTime

import static io.github.mat3e.project.ProjectFixture.*
import static java.util.Collections.emptyList

class ProjectFacadeTest extends Specification {

    def factory = new ProjectFactory()
    def repository = new ProjectRepositoryImpl()
    def taskFacade = Mock(TaskFacade)

    @Subject
    def facade = new ProjectFacade(factory, repository, taskFacade)

    @BeforeEach
    void clearDatabase() {
        repository.clear()
    }

    def "should update project step to have done corresponding task"() {
        given:
            repository.save(projectWithStepUndoneTaskWithStepId(93))

        when:
            facade.updateStep(93, true)

        then:
            with(repository.findByNestedStepId(93).get()) {
                it.snapshot.steps.stream()
                        .filter(step -> step.id == 93)
                        .allMatch(step -> step.correspondingTaskDone)
            }
    }

    @Unroll
    def "should handle #state event to set task to be done"() {
        given:
            repository.save(projectWithStepUndoneTaskWithStepId(93))

        when:
            facade.handle(new TaskEvent(new TaskSourceId("93"), state, null))

        then:
            with(repository.findByNestedStepId(93).get()) {
                it.snapshot.steps.stream()
                        .filter(step -> step.id == 93)
                        .allMatch(step -> step.correspondingTaskDone)
            }

        where:
            state << [TaskEvent.State.DELETED, TaskEvent.State.DONE]
    }

    @Unroll
    def "should handle UNDONE event to set task to be undone"() {
        given:
            repository.save(projectWithStepDoneTaskWithStepId(93))

        when:
            facade.handle(new TaskEvent(new TaskSourceId("93"), TaskEvent.State.UNDONE, null))

        then:
            with(repository.findByNestedStepId(93).get()) {
                it.snapshot.steps.stream()
                        .filter(step -> step.id == 93)
                        .allMatch(step -> !step.correspondingTaskDone)
            }
    }

    def "should save project with id when there is not such in repository"() {
        when:
            def result = facade.save(projectDtoWithStepDoneTaskOfProjectIdAndStepId(45, 90))

        then:
            repository.findById(result.id).isPresent()
    }

    def "should throw exception when saving non-existing project with existing step"() {
        when:
            facade.save(projectDtoWithStepDoneTaskOfProjectIdAndStepId(0, 89))

        then:
            def exception = thrown(IllegalStateException)
            exception.message.contains("existing steps")
    }

    def "should save non-existing project with non-existing step"() {
        when:
            def result = facade.save(projectDtoWithStepDoneTaskOfProjectIdAndStepId(0, 0))

        then:
            with(repository.findById(result.id).get()) {
                it.snapshot.steps.size() == 1
            }
    }

    def "should update project data when saving existing one"() {
        given:
            def saved = repository.save(
                    Project.restore(
                            new ProjectSnapshot(99, "old project", List.of(
                                    new ProjectStepSnapshot(20, "desc20", -76, true, false),
                                    new ProjectStepSnapshot(30, "desc30", -45, true, false)
                            ))
                    )
            )
        and:
            def stepsToSave = List.of(
                    ProjectStepDto.create(10, "desc10", -2),
                    ProjectStepDto.create(20, "new-desc20", -3)
            )
            def dtoToSave = ProjectDto.create(saved.snapshot.id, "newproject", stepsToSave)

        when:
            def result = facade.save(dtoToSave)

        then:
            repository.count() == 1
            result.name == "newproject"

            with(repository.findById(result.id).get().snapshot) {
                it.steps.size() == 2
                it.steps.stream()
                        .noneMatch({ step -> step.id == 30 })
                it.steps.stream().filter({ step -> step.id == 10 })
                        .allMatch({
                            step -> step.description == "desc10" && step.daysToProjectDeadline == -2
                        })
                it.steps.stream().filter({ step -> step.id == 20 })
                        .allMatch({
                            step -> step.description == "new-desc20" && step.daysToProjectDeadline == -3
                        })
            }
    }

    def "should create task for project with all done task"() {
        given:
            def saved = repository.save(
                    Project.restore(
                            new ProjectSnapshot(87, "project", List.of(
                                    new ProjectStepSnapshot(10, "desc10", -76, true, true),
                                    new ProjectStepSnapshot(20, "desc20", -45, true, true)
                            ))
                    )
            )

        when:
            facade.createTasks(saved.snapshot.id, ZonedDateTime.now())

        then:
            1 * taskFacade.createTasks(_) >> emptyList()
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

    int count() {
        return database.size()
    }

    void clear() {
        database.clear()
    }
}