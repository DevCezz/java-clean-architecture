package io.github.mat3e.project;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface SqlProjectRepository extends Repository<SqlProject, Integer> {

    Optional<SqlProject> findById(Integer id);

    SqlProject save(SqlProject entity);
}

@org.springframework.stereotype.Repository
class ProjectRepositoryImpl implements ProjectRepository {

    private final SqlProjectRepository sqlProjectRepository;

    ProjectRepositoryImpl(final SqlProjectRepository sqlProjectRepository) {
        this.sqlProjectRepository = sqlProjectRepository;
    }

    @Override
    public Optional<Project> findById(final Integer id) {
        return sqlProjectRepository.findById(id).map(SqlProject::toProject);
    }

    @Override
    public Project save(final Project entity) {
        return sqlProjectRepository.save(SqlProject.from(entity)).toProject();
    }
}

interface SqlProjectStepRepository extends Repository<SqlProjectStep, Integer> {

    void delete(SqlProjectStep entity);
}

@org.springframework.stereotype.Repository
class ProjectStepRepositoryImpl implements ProjectStepRepository {

    private final SqlProjectStepRepository sqlProjectStepRepository;

    ProjectStepRepositoryImpl(final SqlProjectStepRepository sqlProjectStepRepository) {
        this.sqlProjectStepRepository = sqlProjectStepRepository;
    }

    @Override
    public void delete(final ProjectStep entity) {
        sqlProjectStepRepository.delete(SqlProjectStep.from(entity));
    }
}

interface SqlProjectQueryRepository extends ProjectQueryRepository, Repository<SqlProject, Integer> {
}