package io.github.mat3e.project;

import org.springframework.data.repository.Repository;

interface SqlProjectRepository extends ProjectRepository, Repository<Project, Integer> {
}

interface SqlProjectStepRepository extends ProjectStepRepository, Repository<ProjectStep, Integer> {
}

interface SqlProjectQueryRepository extends ProjectQueryRepository, Repository<Project, Integer> {
}