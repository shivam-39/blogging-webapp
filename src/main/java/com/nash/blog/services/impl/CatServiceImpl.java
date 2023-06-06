package com.nash.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.blog.entities.Category;
import com.nash.blog.exceptions.ResourceNotFoundException;
import com.nash.blog.payloads.CatDto;
import com.nash.blog.repositories.CatRepo;
import com.nash.blog.services.CatService;

@Service
public class CatServiceImpl implements CatService {

	@Autowired
	private CatRepo catRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CatDto createCat(CatDto catDto) {
		
		Category cat = this.modelMapper.map(catDto, Category.class);
		Category createdCat = this.catRepo.save(cat);
		
		return this.modelMapper.map(createdCat, CatDto.class);
	}

	@Override
	public CatDto updateCat(CatDto catDto, Integer catId) {
		
		Category cat = this.catRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", catId));
		
		cat.setCatTitle(catDto.getCatTitle());
		cat.setCatDescription(catDto.getCatDescription());
		
		Category updatedCat = this.catRepo.save(cat);
		
		return this.modelMapper.map(updatedCat, CatDto.class);
	}

	@Override
	public CatDto getCat(Integer catId) {
		Category cat = this.catRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", catId));
		return this.modelMapper.map(cat, CatDto.class);
	}

	@Override
	public List<CatDto> getAllCat() {
		List<Category> catList = this.catRepo.findAll();
		List<CatDto> catDtoList= catList.stream().map((c) -> this.modelMapper.map(c, CatDto.class)).collect(Collectors.toList());
		return catDtoList;
	}

	@Override
	public void deleteCat(Integer catId) {
		Category cat = this.catRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", catId));
		this.catRepo.delete(cat);
	}

}
