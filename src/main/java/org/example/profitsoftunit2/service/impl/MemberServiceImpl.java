package org.example.profitsoftunit2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.exception.EntityNotFoundException;
import org.example.profitsoftunit2.exception.EntityValidationException;
import org.example.profitsoftunit2.mapper.MemberMapper;
import org.example.profitsoftunit2.model.dto.MemberDto;
import org.example.profitsoftunit2.model.entity.Member;
import org.example.profitsoftunit2.repository.MemberRepository;
import org.example.profitsoftunit2.service.MemberService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;

	@Override
	public Optional<Member> findByIdAndProjectId(Long id, Long projectId) {
		return memberRepository.findByIdAndProjectId(id, projectId);
	}

	@Override
	public void createMember(MemberDto memberDto) {
		Member member = memberMapper.toEntity(memberDto);
		try {
			memberRepository.save(member);
		} catch (DataIntegrityViolationException ex) {
			log.warn("Database integrity has been compromised");
//			if(ex.getCause() instanceof ConstraintViolationException) //TODO maybe use?
			throw new EntityValidationException("Email already exist", ex);
		}
	}

	@Override
	public List<MemberDto> findAll() {
		return memberMapper.mapAllToDto(memberRepository.findAll());
	}

	@Override
	public List<Member> findAllByIds(Set<Long> ids) {
		return memberRepository.findAllById(ids);
	}

	@Override
	public void deleteById(Long id) {
		if (!memberRepository.existsById(id)) {
			throw new EntityNotFoundException(String.format("Member with id:%d is not found", id));
		}

		memberRepository.deleteById(id);
	}

	@Override
	public MemberDto updateMemberById(MemberDto memberDto, Long id) {
		Optional<Member> byId = memberRepository.findById(id);

		if (byId.isEmpty()) {
			throw new EntityNotFoundException(String.format("Member with id: %d not found", id));
		}

		Member member = updateFields(memberDto, byId.get());
		try {
			Member updatedMember = memberRepository.save(member);
			return memberMapper.toDto(updatedMember);
		} catch (DataIntegrityViolationException ex) {
			throw new EntityValidationException("Email already exist", ex);
		}
	}

	private Member updateFields(MemberDto memberUpdate, Member member) {
		Member memberToUpdate = new Member();
		memberToUpdate.setId(member.getId());
		memberToUpdate.setName(memberUpdate.getName() == null ? member.getName() : memberUpdate.getName());
		memberToUpdate.setEmail(memberUpdate.getEmail() == null ? member.getEmail() : memberUpdate.getEmail());
		memberToUpdate.setProjects(member.getProjects());
		memberToUpdate.setAssignedTasks(member.getAssignedTasks());
		memberToUpdate.setAssignedTasks(member.getCreatedTasks());

		return memberToUpdate;
	}
}
