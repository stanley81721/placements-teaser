package com.interview.service;

import com.interview.model.User;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    
    User save(User user);

}
