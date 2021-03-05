package io.github.mat3e.project;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface SqlProjectRepository extends Repository<ProjectSnapshot, Integer> {

    Optional<ProjectSnapshot> findById(Integer id);

    ProjectSnapshot save(ProjectSnapshot entity);
}

@org.springframework.stereotype.Repository
class ProjectRepositoryImpl implements ProjectRepository {

    private final SqlProjectRepository sqlProjectRepository;
    private final SqlProjectStepRepository sqlProjectStepRepository;

    ProjectRepositoryImpl(final SqlProjectRepository sqlProjectRepository, final SqlProjectStepRepository sqlProjectStepRepository) {
        this.sqlProjectRepository = sqlProjectRepository;
        this.sqlProjectStepRepository = sqlProjectStepRepository;
    }

    @Override
    public Optional<Project> findById(final Integer id) {
        return sqlProjectRepository.findById(id).map(Project::restore);
    }

    @Override
    public Project save(final Project entity) {
        return Project.restore(sqlProjectRepository.save(entity.getSnapshot()));
    }

    @Override
    public void delete(final Project.Step entity) {
        sqlProjectStepRepository.deleteById(entity.getId());
    }
}

interface SqlProjectStepRepository extends Repository<ProjectStepSnapshot, Integer> {

    void deleteById(int id);
}

interface SqlProjectQueryRepository extends ProjectQueryRepository, Repository<ProjectSnapshot, Integer> {
}