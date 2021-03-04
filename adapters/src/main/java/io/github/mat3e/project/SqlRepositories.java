package io.github.mat3e.project;

import org.springframework.data.repository.Repository;

interface SqlProjectRepository extends ProjectRepository, Repository<SqlProject, Integer> {
}

interface SqlProjectStepRepository extends ProjectStepRepository, Repository<SqlProjectStep, Integer> {
}

interface SqlProjectQueryRepository extends ProjectQueryRepository, Repository<SqlProject, Integer> {
}