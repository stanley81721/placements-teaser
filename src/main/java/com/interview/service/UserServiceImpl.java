package com.interview.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.interview.config.WebSecurityConfiguration;
import com.interview.model.Role;
import com.interview.model.User;
import com.interview.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
	public User save(User tmpUser) {
        WebSecurityConfiguration webSecurityConfiguration = new WebSecurityConfiguration();
		User user = new User(tmpUser.getUsername(), tmpUser.getEmail(), webSecurityConfiguration.passwordEncoder().encode(tmpUser.getPassword()), Arrays.asList(new Role("ROLE_USER")));
		
		return this.userRepository.save(user);
	}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
    
}
