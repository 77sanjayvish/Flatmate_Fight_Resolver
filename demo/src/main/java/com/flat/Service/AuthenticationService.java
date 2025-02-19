package com.flat.Service;

import com.flat.entity.User;
import com.flat.payload.AuthenticationResponse;
import com.flat.payload.LoginDto;
import com.flat.payload.RefreshTokenResponse;
import com.flat.payload.SignUpDto;

public interface AuthenticationService {
    User signup(SignUpDto signUpDto);

    AuthenticationResponse login(LoginDto loginDto);

    AuthenticationResponse refreshToken (RefreshTokenResponse refresshTokenResponse );
}
