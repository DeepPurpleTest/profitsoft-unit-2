package org.example.profitsoftunit2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.mapper.MemberMapper;
import org.example.profitsoftunit2.model.dto.MemberDto;
import org.example.profitsoftunit2.model.entity.Member;
import org.example.profitsoftunit2.repository.MemberRepository;
import org.example.profitsoftunit2.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;

	@Override
	public Optional<Member> findById(Long id) {
		return memberRepository.findById(id);
	}

	@Override
	public void createMember(MemberDto memberDto) {
		Member member = memberMapper.toEntity(memberDto);
		memberRepository.save(member);
	}

	@Override
	public List<MemberDto> findAllByProjectId(Long id) {
		return memberMapper.mapAllToDto(memberRepository.findByProjectId(id));
	}
}
