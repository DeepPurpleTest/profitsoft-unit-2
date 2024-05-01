package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.dto.MemberDto;
import org.example.profitsoftunit2.model.entity.Member;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MemberService {

	Optional<Member> findByIdAndProjectId(Long id, Long projectId);

	void createMember(MemberDto memberDto);

	List<MemberDto> findAll();

	List<Member> findAllByIds(Set<Long> ids);

	void deleteById(Long id);

	MemberDto updateMemberById(MemberDto memberDto, Long id);
}
