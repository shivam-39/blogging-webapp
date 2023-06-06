package com.nash.blog.services;

import java.util.List;

import com.nash.blog.payloads.PostDto;
import com.nash.blog.payloads.PostResponse;

public interface PostService {

	//create
	PostDto createPost(PostDto postDto, Integer userId, Integer catId);
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	void deletPost(Integer postId);
	
	//get single post
	PostDto getPostById(Integer postId); 
	
	//get all post
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//get all by category
	List<PostDto> getAllByCategory(Integer catId);
	
	//get all by users
	List<PostDto> getAllByUser(Integer userId);
	
	//search posts
	List<PostDto> searchPostByTitle(String keyword);
	
}
