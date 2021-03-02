package io.github.mat3e.project;

interface ProjectStepRepository {

    <S extends ProjectStep> void delete(S entity);
}
