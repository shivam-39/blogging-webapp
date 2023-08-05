package com.nash.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nash.blog.payloads.ApiResponse;
import com.nash.blog.payloads.CatDto;
import com.nash.blog.services.CatService;

@RestController
@RequestMapping("/api/v1/category")
public class CatController {
	
	@Autowired
	CatService catService;
	
	@PostMapping("/")
	public ResponseEntity<CatDto> createCat(@Valid @RequestBody CatDto catDto){
		CatDto createdCatDto = this.catService.createCat(catDto);
		return new ResponseEntity<>(createdCatDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CatDto> updateCat(@Valid @RequestBody CatDto catDto, @PathVariable Integer id){
		CatDto updatedCatDto = this.catService.updateCat(catDto, id);
		return new ResponseEntity<>(updatedCatDto, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CatDto> getCat(@PathVariable Integer id){
		CatDto catDto = this.catService.getCat(id);
		return new ResponseEntity<>(catDto, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CatDto>> getAllCat(){
		List<CatDto> catDtoList = this.catService.getAllCat();
		return new ResponseEntity<>(catDtoList, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCat(@PathVariable Integer id){
		this.catService.deleteCat(id);
		return new ResponseEntity<>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
	}
}
