package com.elasticsearch.service.impl;

import com.elasticsearch.document.User;
import com.elasticsearch.repository.UserRepository;
import com.elasticsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public User findById(final String id) {
        return userRepository
                .findById(id).orElseThrow(() -> new IllegalStateException());
    }

    @Override
    public void save(final User user) {
        userRepository.save(user);
    }
}
