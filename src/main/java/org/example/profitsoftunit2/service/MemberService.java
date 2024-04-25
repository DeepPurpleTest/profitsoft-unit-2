package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.dto.MemberDto;
import org.example.profitsoftunit2.model.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

	Optional<Member> findById(Long id);

	void createMember(MemberDto memberDto);

	List<MemberDto> findAllByProjectId(Long id);
}
