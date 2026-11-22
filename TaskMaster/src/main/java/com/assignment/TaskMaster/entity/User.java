package com.assignment.TaskMaster.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userid;
	@NotNull
	private String username;
	@NotNull
	@Column(unique = true)
	private String email;
	private String phone;
	@NotNull
	private String password;
	
	@NotNull
	@Column(updatable = false,nullable=false)
	@Setter(AccessLevel.NONE)
	private LocalDateTime createdAt;
	
}
