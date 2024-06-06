package com.example.ProjectJava.repositories;

import com.example.ProjectJava.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
