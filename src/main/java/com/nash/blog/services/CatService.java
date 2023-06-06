package com.nash.blog.services;

import java.util.List;

import com.nash.blog.payloads.CatDto;

public interface CatService {

	CatDto createCat(CatDto catDto);
	
	public abstract CatDto updateCat(CatDto catDto, Integer catId);
	
	public CatDto getCat(Integer catId);
	
	public List<CatDto> getAllCat();
	
	public void deleteCat(Integer catId);
}
