package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.dto.MemberDto;
import org.example.profitsoftunit2.model.entity.Member;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MemberService {

	Optional<Member> findById(Long id);

	void createMember(MemberDto memberDto);

	List<MemberDto> findAll();

	List<MemberDto> findAllByProjectId(Long id);

	List<Member> findAllByIds(Set<Long> ids);

	boolean existsById(Long id);

	Long deleteById(Long id);

	void updateMemberById(MemberDto memberDto, Long id);
}
