package com.vsms.portal.controller;

import com.vsms.portal.api.requests.LoginRequest;
import com.vsms.portal.data.model.User;
import com.vsms.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin()
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {
        User user = userService.login(authenticationRequest);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody() User user) {
        user = userService.signUp(user);
        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user, HttpStatus.valueOf(407));
        }
    }

    @GetMapping("/encode")
    public ResponseEntity<String> encode(@RequestParam() Map<String, String> requestMap) {
        String encoded = userService.encode(requestMap.get("password"));
        return new ResponseEntity<String>(encoded, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


}
