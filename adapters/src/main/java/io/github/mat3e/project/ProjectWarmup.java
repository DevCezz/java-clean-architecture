package io.github.mat3e.project;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
class ProjectWarmup implements ApplicationListener<ContextRefreshedEvent> {

    private final ProjectFacade projectFacade;

    ProjectWarmup(final ProjectFacade projectFacade) {
        this.projectFacade = projectFacade;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        projectFacade.initializeData();
    }
}
