package com.vsms.portal.service;

import ch.qos.logback.core.util.StringCollectionUtil;
import com.vsms.portal.api.requests.LoginRequest;
import com.vsms.portal.data.model.User;
import com.vsms.portal.data.repositories.UserRepository;
import com.vsms.portal.utils.auth.JwtTokenUtil;
import com.vsms.portal.utils.auth.JwtUserDetailsService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    private JwtUserDetailsService userDetailsService;

    private CacheService cache;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService, CacheService cache, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.cache = cache;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public User login(LoginRequest loginRequest) throws Exception {
        authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginRequest.getEmail());
        User user = (User) cache.getStore().get(userDetails.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        user.setToken(token);
        return user;
    }

    public User signUp(User user) {
        // TODO -> validate and send email first
        user.setPassword(passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(10)));
        return userRepository.save(user);
    }

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
