package org.interswitch.bookstore.service;


import org.interswitch.bookstore.dto.request.AuthenticationRequest;
import org.interswitch.bookstore.dto.request.RegisterRequest;
import org.interswitch.bookstore.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
