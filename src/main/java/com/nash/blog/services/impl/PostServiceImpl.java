package com.nash.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nash.blog.entities.Category;
import com.nash.blog.entities.Post;
import com.nash.blog.entities.User;
import com.nash.blog.exceptions.ResourceNotFoundException;
import com.nash.blog.payloads.PostDto;
import com.nash.blog.payloads.PostResponse;
import com.nash.blog.repositories.CatRepo;
import com.nash.blog.repositories.PostRepo;
import com.nash.blog.repositories.UserRepo;
import com.nash.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CatRepo catRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer catId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		Category category = this.catRepo.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Category", "catId", catId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post createdPost = this.postRepo.save(post);
				
		return this.modelMapper.map(createdPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setTitle(postDto.getTitle());
		Category category = this.catRepo.findById(postDto.getCategory().getCatId()).get();
		post.setCategory(category);
		Post updatPost = this.postRepo.save(post);
		return this.modelMapper.map(updatPost, PostDto.class);
	}

	@Override
	public void deletPost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> postList = pagePost.getContent();
		List<PostDto> postDtoList = postList.stream().map(po-> this.modelMapper.map(po, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtoList);
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalPages());
		return postResponse;
	}

	@Override
	public List<PostDto> getAllByCategory(Integer catId) {
		Category cat = this.catRepo.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Category", "catId", catId));
		List<Post> postList = this.postRepo.findByCategory(cat);
		List<PostDto> postDtoList = postList.stream().map(p-> this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDto> getAllByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		List<Post> postList = this.postRepo.findByUser(user);
		List<PostDto> postDtoList = postList.stream().map(p-> this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDto> searchPostByTitle(String keyword) {
//		List<Post> postList = this.postRepo.findByTitleContaining(keyword);
		List<Post> postList = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtoList = postList.stream().map(p-> this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		return postDtoList;
	}

}
