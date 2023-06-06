package com.nash.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nash.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
