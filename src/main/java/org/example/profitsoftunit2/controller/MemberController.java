package org.example.profitsoftunit2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.exception.EntityValidationException;
import org.example.profitsoftunit2.model.dto.MemberDto;
import org.example.profitsoftunit2.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for entity Member
 */
@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody @Valid MemberDto memberDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new EntityValidationException("Incorrect member data", bindingResult);
		}

		memberService.createMember(memberDto);
	}

	@GetMapping()
	public List<MemberDto> findAll() {
		return memberService.findAll();
	}

	@PutMapping("/{id}")
	public MemberDto updateMember(@PathVariable("id") Long id, @RequestBody @Valid MemberDto memberDto,
								  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new EntityValidationException("Incorrect Member data", bindingResult);
		}

		return memberService.updateMemberById(memberDto, id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteMember(@PathVariable("id") Long id) {
		memberService.deleteById(id);
	}
}
