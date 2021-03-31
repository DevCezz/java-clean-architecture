package io.github.mat3e.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Config.class)
@ActiveProfiles("test")
@Rollback
class ProjectRepositoryTest {

    @Autowired
    private SqlProjectRepository sqlProjectRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void should_find_project_when_saved_in_db() {
        projectRepository.save(Project.restore(
                new ProjectSnapshot(1, "project", Collections.emptyList())
        ));

        assertThat(sqlProjectRepository.findById(1)).isPresent();
    }
}

@Configuration
@EnableAutoConfiguration
class Config {

    @Bean
    ProjectRepository projectRepository(
            SqlProjectRepository sqlProjectRepository,
            SqlProjectStepRepository sqlProjectStepRepository
    ) {
        return new ProjectRepositoryImpl(sqlProjectRepository, sqlProjectStepRepository);
    }
}