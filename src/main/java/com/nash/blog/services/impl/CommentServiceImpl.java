package com.nash.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.blog.entities.Comment;
import com.nash.blog.entities.Post;
import com.nash.blog.exceptions.ResourceNotFoundException;
import com.nash.blog.payloads.CommentDto;
import com.nash.blog.repositories.CommentRepo;
import com.nash.blog.repositories.PostRepo;
import com.nash.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment createdComment = this.commentRepo.save(comment);
		CommentDto createdCommentDto = this.modelMapper.map(createdComment, CommentDto.class);
		return createdCommentDto;
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
		this.commentRepo.delete(comment);
	}

}
