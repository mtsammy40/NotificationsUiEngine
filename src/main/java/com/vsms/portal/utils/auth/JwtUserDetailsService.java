package com.vsms.portal.utils.auth;

import com.vsms.portal.data.model.User;
import com.vsms.portal.data.repositories.UserRepository;
import com.vsms.portal.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    private CacheService cache;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository, CacheService cache) {
        this.userRepository = userRepository;
        this.cache = cache;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        userOptional.orElseThrow(() -> new UsernameNotFoundException(email));
        User user = userOptional.get();
        this.cache.getStore().put(user.getEmail(), user);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
