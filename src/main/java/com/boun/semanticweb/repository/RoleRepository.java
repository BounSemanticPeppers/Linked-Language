package com.boun.semanticweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boun.semanticweb.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
