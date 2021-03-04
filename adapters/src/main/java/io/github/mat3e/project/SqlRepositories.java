package io.github.mat3e.project;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface SqlProjectRepository extends Repository<SqlProject, Integer> {

    Optional<SqlProject> findById(Integer id);

    Project save(SqlProject entity);
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
        return sqlProjectRepository.save(SqlProject.from(entity));
    }
}

interface SqlProjectStepRepository extends ProjectStepRepository, Repository<SqlProjectStep, Integer> {
}

interface SqlProjectQueryRepository extends ProjectQueryRepository, Repository<SqlProject, Integer> {
}