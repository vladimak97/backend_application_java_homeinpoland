package com.service1.demo.repositories;

import com.service1.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// interface
@Repository // this annotation lets spring know that this is a repository and at the same time makes it a bean managed by spring
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    void deleteByUsername(String username);
}
