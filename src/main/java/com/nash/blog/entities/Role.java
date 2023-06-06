package com.nash.blog.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class Role {

	@Id
	private Integer id;
	
	private String roleName;
	
	@ManyToMany(mappedBy = "roleSet")
	private Set<User> userSet = new HashSet<>();
}
