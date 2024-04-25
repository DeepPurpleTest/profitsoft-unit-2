package org.example.profitsoftunit2.repository;

import org.example.profitsoftunit2.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query("SELECT m FROM Member m JOIN m.projects p WHERE p.id = :projectId")
	List<Member> findByProjectId(@Param("projectId") Long projectId);
}
