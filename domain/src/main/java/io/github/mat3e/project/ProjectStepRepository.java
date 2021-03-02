package io.github.mat3e.project;

import org.springframework.data.repository.Repository;

interface ProjectStepRepository extends Repository<ProjectStep, Integer> {

    <S extends ProjectStep> void delete(S entity);
}
