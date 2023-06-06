package com.nash.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nash.blog.entities.Category;

public interface CatRepo extends JpaRepository<Category, Integer> {

}
