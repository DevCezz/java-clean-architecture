package io.github.mat3e.project;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component("projectWarmup")
class Warmup implements ApplicationListener<ContextRefreshedEvent> {

    private final ProjectFacade projectFacade;

    Warmup(final ProjectFacade projectFacade) {
        this.projectFacade = projectFacade;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        projectFacade.initializeData();
    }
}
