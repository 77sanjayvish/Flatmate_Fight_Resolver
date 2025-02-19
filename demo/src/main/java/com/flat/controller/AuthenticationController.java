package com.flat.controller;

import com.flat.Service.AuthenticationService;
import com.flat.entity.User;
import com.flat.payload.AuthenticationResponse;
import com.flat.payload.LoginDto;
import com.flat.payload.RefreshTokenResponse;
import com.flat.payload.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@RequestBody SignUpDto signUpDto)
    {
        return  ResponseEntity.ok(authenticationService.signup(signUpDto));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody LoginDto loginDto){

        return ResponseEntity.ok(authenticationService.login(loginDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenResponse refresshTokenResponse){

        return ResponseEntity.ok(authenticationService.refreshToken(refresshTokenResponse));
    }

}
