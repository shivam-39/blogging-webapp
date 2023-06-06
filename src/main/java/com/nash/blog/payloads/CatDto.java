package com.nash.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CatDto {

	private Integer catId;
	
	@NotBlank
	@Size(min = 4, message = "Min size should be 4")
	private String catTitle;
	
	@NotBlank
	@Size(min = 3)
	private String catDescription;
	
}
