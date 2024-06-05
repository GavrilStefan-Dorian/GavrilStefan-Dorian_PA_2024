package com.example.ProjectJava.repositories;

import com.example.ProjectJava.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepositoryJPA extends JpaRepository<AppUser, Integer> {
    public AppUser findByUserName(String userName);
    public AppUser findByEmail(String email);

}
