package com.vsms.portal.controller;

import com.vsms.portal.api.requests.LoginRequest;
import com.vsms.portal.api.responses.ApiResponse;
import com.vsms.portal.data.model.User;
import com.vsms.portal.service.UserService;
import com.vsms.portal.utils.enums.ApiStatus;
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
    public ResponseEntity<ApiResponse<User>> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {
        User user = userService.login(authenticationRequest);
        if (user != null) {
            return new ApiResponse<User>(user).build();
        } else {
            return new ApiResponse<User>(ApiStatus.INVALID_CREDENTIALS, null).build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> signUp(@RequestBody() User user) throws Exception {
        user = userService.signUp(user);
        if(user != null) {
            return new ApiResponse<User>(user).build();
        } else {
            return new ApiResponse<User>(ApiStatus.BAD_REQUEST, null).build();
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
