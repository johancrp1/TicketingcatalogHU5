package com.example.ticketingcatalog.infrastructure.service;

import com.example.ticketingcatalog.domain.model.UserModel;
import com.example.ticketingcatalog.infrastructure.adapters.out.jpa.entity.UserEntity;
import com.example.ticketingcatalog.infrastructure.adapters.out.jpa.repository.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    public UserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }

    // ------------------- Métodos Auxiliares -------------------

    public UserModel loadUserByUsernameModel(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Set<String> roles = new HashSet<>(user.getRoles());
        return new UserModel(user.getId(), user.getUsername(), user.getPassword(), roles);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void saveUser(UserModel userModel) {
        UserEntity entity = new UserEntity();
        entity.setUsername(userModel.getUsername());
        entity.setPassword(userModel.getPassword());
        entity.setRoles(userModel.getRoles());
        userRepository.save(entity);
    }
}
