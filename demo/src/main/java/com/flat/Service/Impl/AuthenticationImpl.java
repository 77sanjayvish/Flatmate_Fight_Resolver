package com.flat.Service.Impl;

import com.flat.Service.AuthenticationService;
import com.flat.Service.JwtService;
import com.flat.entity.User;
import com.flat.payload.AuthenticationResponse;
import com.flat.payload.LoginDto;
import com.flat.payload.RefreshTokenResponse;
import com.flat.payload.SignUpDto;
import com.flat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service

public class AuthenticationImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User signup(SignUpDto signUpDto) {
        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setFlatCode(signUpDto.getFlatCode());
        user.setRole(signUpDto.getRole());
        return userRepository.save(user);
    }

    @Override
    public AuthenticationResponse login(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
            var user = (User)userRepository.findByEmail(loginDto.getEmail());
            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
                    authenticationResponse.setToken(jwt);
                    authenticationResponse.setRefreshToken(refreshToken);
            return authenticationResponse;

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid Username or Password");
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenResponse refresshTokenResponse) {
        try{
            String userEmail= jwtService.extractUserName(refresshTokenResponse.getToken());
            User user = (User) userRepository.findByEmail(userEmail);
            if(jwtService.isTokenValid(refresshTokenResponse.getToken(),user)){
                var jwt = jwtService.generateToken(user);
                AuthenticationResponse authenticationResponse = new AuthenticationResponse();
                authenticationResponse.setToken(jwt);
                authenticationResponse.setRefreshToken(refresshTokenResponse.getToken());
                return authenticationResponse;
            }
        }catch(Exception e){
            e.printStackTrace();
          return null;
        }
        return null;
    }
}
