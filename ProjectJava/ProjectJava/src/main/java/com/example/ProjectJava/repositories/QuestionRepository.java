package com.example.ProjectJava.repositories;

import com.example.ProjectJava.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByDomainId(Long domainId);
    Optional<Question> findByIdAndDomainId(Long questionId, Long domainId);
}

