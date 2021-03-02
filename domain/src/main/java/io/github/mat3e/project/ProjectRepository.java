package io.github.mat3e.project;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface ProjectRepository extends Repository<Project, Integer> {

    Optional<Project> findById(int id);

    <S extends Project> S save(S entity);
}
