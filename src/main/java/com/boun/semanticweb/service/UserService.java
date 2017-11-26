package com.boun.semanticweb.service;

import com.boun.semanticweb.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
    
    String findByUserId(Long userId);
}
