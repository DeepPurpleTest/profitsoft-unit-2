package org.example.profitsoftunit2.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.exception.EntityNotFoundException;
import org.example.profitsoftunit2.exception.EntityValidationException;
import org.example.profitsoftunit2.exception.UnknownApiException;
import org.example.profitsoftunit2.mapper.MemberMapper;
import org.example.profitsoftunit2.model.dto.MemberDto;
import org.example.profitsoftunit2.model.entity.Member;
import org.example.profitsoftunit2.repository.MemberRepository;
import org.example.profitsoftunit2.service.MemberService;
import org.hibernate.exception.ConstraintViolationException;
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

		save(member);
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
	@Transactional
	public void deleteById(Long id) {
		Optional<Member> byId = memberRepository.findById(id);
		if (byId.isEmpty()) {
			throw new EntityNotFoundException(String.format("Member with id:%d is not found", id));
		}

		Member member = byId.get();
		member.getProjects().forEach(project -> project.getMembers().remove(member));
		member.getAssignedTasks().forEach(task -> task.setAssignee(null));
		member.getCreatedTasks().forEach(task -> task.setReporter(null));

		memberRepository.deleteById(id);
	}

	@Override
	@Transactional
	public MemberDto updateMemberById(MemberDto memberDto, Long id) {
		Optional<Member> byId = memberRepository.findById(id);

		if (byId.isEmpty()) {
			throw new EntityNotFoundException(String.format("Member with id: %d not found", id));
		}

		Member member = updateFields(memberDto, byId.get());
		return memberMapper.toDto(save(member));
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

	private Member save(Member member) {
		try {
			return memberRepository.save(member);
		} catch (DataIntegrityViolationException ex) {
			log.warn("Database integrity has been compromised");
			if(ex.getCause() instanceof ConstraintViolationException) {
				throw new EntityValidationException("Email already exist");
			}

			throw new UnknownApiException("Db exception while saving");
		}
	}
}
