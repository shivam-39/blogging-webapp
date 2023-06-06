package com.nash.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nash.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
