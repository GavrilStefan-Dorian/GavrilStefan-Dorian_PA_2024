package com.example.ProjectJava.repositories;

import com.example.ProjectJava.entities.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserResponseRepository extends JpaRepository<UserResponse, Long> {
    List<UserResponse> findByDomainIdAndQuestionId(Long domainId, Long questionId);
}
