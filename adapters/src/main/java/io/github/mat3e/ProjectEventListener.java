package io.github.mat3e;

import io.github.mat3e.project.ProjectFacade;
import io.github.mat3e.task.vo.TaskEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
class ProjectEventListener {
    private final ProjectFacade projectFacade;

    ProjectEventListener(final ProjectFacade projectFacade) {
        this.projectFacade = projectFacade;
    }

    @EventListener
    // warning: must be synchronous in current design
    public void on(TaskEvent event) {
        projectFacade.handle(event);
    }
}
