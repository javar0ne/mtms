package com.dg.mtms.server.service;

import com.dg.mtms.server.Singleton;
import com.dg.mtms.server.model.User;
import com.dg.mtms.server.repository.UserRepository;

import java.util.Optional;

public class UserService extends Singleton<UserService> {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void createInstance(UserRepository userRepository) {
        addInstance(new UserService(userRepository));
    }

    public static UserService getInstance() {
        return Singleton.getInstance(UserService.class);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
