package com.nash.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nash.blog.entities.Category;
import com.nash.blog.entities.Post;
import com.nash.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
//	List<Post> findByTitleContaining(String title);
	
	@Query("SELECT p FROM Post p WHERE p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);
	
}
