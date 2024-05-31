package org.example.Lab11.services;

import org.example.Lab11.entities.AppUser;
import org.example.Lab11.repositories.AppUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {
    @Autowired
    private AppUserRepositoryJPA userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepo.findByUserName(username);

        if(appUser != null) {
            return User.withUsername(appUser.getUserName())
                    .password(appUser.getPassword())
                    .roles(appUser.getRole())
                    .build();
        }
        return null;
    }
}
