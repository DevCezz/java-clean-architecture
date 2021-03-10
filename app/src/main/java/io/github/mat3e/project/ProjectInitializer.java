package io.github.mat3e.project;

import java.util.List;

class ProjectInitializer {

    private final ProjectRepository projectRepository;
    private final ProjectQueryRepository projectQueryRepository;

    ProjectInitializer(final ProjectRepository projectRepository, final ProjectQueryRepository projectQueryRepository) {
        this.projectRepository = projectRepository;
        this.projectQueryRepository = projectQueryRepository;
    }

    void init() {
        if (projectQueryRepository.count() == 0) {
            var steps = List.of(
                    new ProjectStepSnapshot(0, "First", -3, false, false),
                    new ProjectStepSnapshot(0, "Second", -2, false, false),
                    new ProjectStepSnapshot(0, "Third", 0, false, false)
            );
            var project = Project.restore(new ProjectSnapshot(0, "Example project", steps));

            projectRepository.save(project);
        }
    }
}
