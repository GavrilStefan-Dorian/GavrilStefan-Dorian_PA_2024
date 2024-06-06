package com.example.ProjectJava.repositories;

import com.example.ProjectJava.entities.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<Domain, Long> {
}
