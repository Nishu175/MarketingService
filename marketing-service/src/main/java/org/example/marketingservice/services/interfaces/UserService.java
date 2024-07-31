package org.example.marketingservice.services.interfaces;

import org.example.marketingservice.models.User;

import java.util.List;

public interface UserService {
    User save(User user);

    User findById(Long id);

    List<User> findAll();

    List<User> findAllByUserIds(List<Long> userId);
}
