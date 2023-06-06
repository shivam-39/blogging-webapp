package com.nash.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nash.blog.payloads.ApiResponse;
import com.nash.blog.payloads.PostDto;
import com.nash.blog.payloads.PostResponse;
import com.nash.blog.services.FileService;
import com.nash.blog.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	PostService postService;
	
	@Autowired
	FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{catId}")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer catId
			){
		PostDto createdPostDto = this.postService.createPost(postDto, userId, catId);
		return new ResponseEntity<PostDto>(createdPostDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		this.postService.deletPost(postId);
		return new ApiResponse("Post deleted successfully", true);
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}
	
	@GetMapping("")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = "postId", required = false) String sortBy,
			@RequestParam(name = "sortDir", defaultValue = "asc", required = false)String sortDir
			){
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable Integer userId){
		List<PostDto> postDtoList = this.postService.getAllByUser(userId);
		return new ResponseEntity<>(postDtoList, HttpStatus.OK);
	}
	
	@GetMapping("/category/{catId}")
	public ResponseEntity<List<PostDto>> getAllPostByCategory(@PathVariable Integer catId){
		List<PostDto> postDtoList = this.postService.getAllByCategory(catId);
		return new ResponseEntity<>(postDtoList, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<PostDto>> searchByPostTitle(@RequestParam(name = "keyword", required = false) String keyword){
		List<PostDto> postDtoList = this.postService.searchPostByTitle(keyword);
		return new ResponseEntity<>(postDtoList, HttpStatus.OK);
	}
	
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable("postId") Integer postId
			) throws IOException{
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePostDto = postService.updatePost(postDto, postId);
		return new ResponseEntity<>(updatePostDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException {
		InputStream resource = this.fileService.getResourec(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
