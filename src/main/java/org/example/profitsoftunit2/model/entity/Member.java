package org.example.profitsoftunit2.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ManyToMany
	@JoinTable(
			name = "member_project",
			joinColumns = @JoinColumn(name = "member_id"),
			inverseJoinColumns = @JoinColumn(name = "project_id")
	)
	@ToString.Exclude
	private Set<Project> projects = new HashSet<>();

	@OneToMany(mappedBy = "reporter")
	@ToString.Exclude
	private Set<Task> createdTasks = new HashSet<>();

	@OneToMany(mappedBy = "assignee")
	@ToString.Exclude
	private Set<Task> assignedTasks = new HashSet<>();
}
