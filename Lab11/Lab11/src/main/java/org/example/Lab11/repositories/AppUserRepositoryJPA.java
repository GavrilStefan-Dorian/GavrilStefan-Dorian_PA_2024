package org.example.Lab11.repositories;

import org.example.Lab11.entities.AppUser;
import org.hibernate.metamodel.model.domain.internal.AbstractAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepositoryJPA extends JpaRepository<AppUser, Integer> {
    public AppUser findByUserName(String userName);

}
