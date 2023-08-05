package com.nash.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
	
	private Integer id;
	
	@NotEmpty
	@Size(max=12, message="Max length of name be should be 12")
	private String name;
	
	@Email(message="Not a valid email")
	private String email;
	
	@NotEmpty
	@Size(min=4, max=15, message="Password length should be from 2 to 15")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roleSet = new HashSet<>();
	
	@JsonProperty
	private void setPassword(String password) {
		this.password = password;
	}
	
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
}
