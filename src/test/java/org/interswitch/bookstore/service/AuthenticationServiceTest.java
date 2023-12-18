package org.interswitch.bookstore.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.interswitch.bookstore.dto.AuthorDto;
import org.interswitch.bookstore.dto.BookDto;
import org.interswitch.bookstore.dto.request.AuthenticationRequest;
import org.interswitch.bookstore.dto.request.AuthorRequest;
import org.interswitch.bookstore.dto.request.BookRequest;
import org.interswitch.bookstore.dto.request.RegisterRequest;
import org.interswitch.bookstore.dto.response.AmazonResponse;
import org.interswitch.bookstore.dto.response.AuthenticationResponse;
import org.interswitch.bookstore.dto.response.PagedResponse;
import org.interswitch.bookstore.entities.Author;
import org.interswitch.bookstore.entities.Book;
import org.interswitch.bookstore.entities.RefreshToken;
import org.interswitch.bookstore.entities.User;
import org.interswitch.bookstore.enums.Genre;
import org.interswitch.bookstore.enums.Role;
import org.interswitch.bookstore.enums.TokenType;
import org.interswitch.bookstore.exception.GenericException;
import org.interswitch.bookstore.repository.BookRepository;
import org.interswitch.bookstore.repository.UserRepository;
import org.interswitch.bookstore.service.impl.AuthenticationServiceImpl;
import org.interswitch.bookstore.service.impl.BookServiceImpl;
import org.interswitch.bookstore.service.storage.StorageService;
import org.interswitch.bookstore.service.storage.UploadObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class AuthenticationServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private RefreshTokenService refreshTokenService;
    private AuthenticationService authenticationService;
    private RegisterRequest registerRequest;
    private User user;
    private RefreshToken refreshToken;
    private AuthenticationRequest authenticationRequest;


    @BeforeEach
    void setUp() throws IOException {
        authenticationService = new AuthenticationServiceImpl(
               passwordEncoder,jwtService,userRepository,authenticationManager,refreshTokenService
        );

        registerRequest= new  RegisterRequest();
        registerRequest.setFirstName("James");
        registerRequest.setLastName("John");
        registerRequest.setEmail("ama@gmail.com");
        registerRequest.setPassword("Amaka@123");
        registerRequest.setRole(Role.ADMIN);

        user = new User();
        user.setFirstname("James");
        user.setLastname("John");
        user.setEmail("ama@gmail.com");
        user.setPassword("abcdefg");
        user.setRole(Role.ADMIN);

        refreshToken = new RefreshToken();
        refreshToken.setToken("abcd");
        refreshToken.setUser(user);
        refreshToken.setRevoked(false);
        refreshToken.setExpiryDate(Instant.now().plusSeconds(100));
        authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail(registerRequest.getEmail());
        authenticationRequest.setPassword(registerRequest.getPassword());
    }

    @Test
    public void testThatUserCanRegister(){
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("abcdefg");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwt");
        when(refreshTokenService.createRefreshToken(anyLong())).thenReturn(refreshToken);

        AuthenticationResponse response  = authenticationService.register(registerRequest);
        assertThat(response.getId()).isEqualTo(user.getId());
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
    }


    @Test
    public void testThatUsersWithSameEmailCannotRegister(){
        RegisterRequest registerRequest2= new  RegisterRequest();
        registerRequest2.setFirstName("Micheele");
        registerRequest2.setLastName("AMaka");
        registerRequest2.setEmail("ama@gmail.com");
        registerRequest2.setPassword("Amaka@123");
        registerRequest2.setRole(Role.ADMIN);

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("abcdefg");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwt");
        when(refreshTokenService.createRefreshToken(anyLong())).thenReturn(refreshToken);
        authenticationService.register(registerRequest);
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        assertThrows(GenericException.class, ()->authenticationService.register(registerRequest2));

    }

    @Test
    public void testThatUserCanLogin(){
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()))).thenReturn(null);
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwt");
        when(refreshTokenService.createRefreshToken(anyLong())).thenReturn(refreshToken);

        AuthenticationResponse response  = authenticationService.authenticate(authenticationRequest);
        assertThat(response.getId()).isEqualTo(user.getId());
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
    }


}
