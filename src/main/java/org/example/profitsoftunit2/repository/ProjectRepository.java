package org.example.profitsoftunit2.repository;

import org.example.profitsoftunit2.model.entity.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findAllBy(Pageable pageable);
}
