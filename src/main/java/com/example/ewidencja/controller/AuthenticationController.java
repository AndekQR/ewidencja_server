package com.example.ewidencja.controller;

import com.example.ewidencja.helper.AuthorityType;
import com.example.ewidencja.helper.UserAlreadyInDatabaseException;
import com.example.ewidencja.model.JwtRefreshToken;
import com.example.ewidencja.model.User;
import com.example.ewidencja.security.AuthenticationRequest;
import com.example.ewidencja.security.jwt.*;
import com.example.ewidencja.service.interfaces.AuthorityService;
import com.example.ewidencja.service.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private AuthorityService authorityService;
    private final JwtRefreshTokenProvider jwtRefreshTokenProvider;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, AuthorityService authorityService, JwtRefreshTokenProvider jwtRefreshTokenProvider) {
        this.authenticationManager=authenticationManager;
        this.jwtTokenProvider=jwtTokenProvider;
        this.userService=userService;
        this.authorityService=authorityService;
        this.jwtRefreshTokenProvider=jwtRefreshTokenProvider;
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtAuthenticationResponse> refreshAccessToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws InvalidJwtAuthenticationException {
        if (jwtRefreshTokenProvider.isValid(refreshTokenRequest.getRefreshToken())){
            Optional<User> user = jwtRefreshTokenProvider.getUser(refreshTokenRequest.getRefreshToken());
            if (user.isPresent()){
                String accessToken = jwtTokenProvider.createToken(user.get().getUsername(), user.get().getAuthoritiesList());
                return ResponseEntity.ok(new JwtAuthenticationResponse(accessToken, refreshTokenRequest.getRefreshToken(), jwtTokenProvider.getExpirationDate(accessToken).getTime(), user.get()));
            }
        }
        throw new InvalidJwtAuthenticationException("Invalid Refresh Token");
    }


    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody AuthenticationRequest data) {
        String username = data.getUsername();
        User user = userService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username "+username+" not found"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
        String token = jwtTokenProvider.createToken(username, user.getAuthoritiesList());
        JwtRefreshToken refreshToken = jwtRefreshTokenProvider.createRefreshToken(user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(token);
        jwtAuthenticationResponse.setExpires(jwtTokenProvider.getExpirationDate(token).getTime());
        jwtAuthenticationResponse.setRefreshToken(refreshToken.getToken());
        jwtAuthenticationResponse.setUser(user);

        return ResponseEntity.ok(jwtAuthenticationResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<Object, Object>> register(@RequestBody User user) throws UserAlreadyInDatabaseException {

        if (userService.findByEmail(user.getEmail()).orElse(null) == null) {
            user.setAuthorities(authorityService.createOrGetAuthorities(new AuthorityType[]{AuthorityType.ROLE_USER}));
            user.setVerified(true);
            userService.save(user);
        } else {
            throw new UserAlreadyInDatabaseException("User already in database");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/logout")
    public ResponseEntity.BodyBuilder logout(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> user = userService.findByEmail(userDetails.getUsername());
        if (user.isPresent()){
            jwtRefreshTokenProvider.deleteOldToken(user.get());
            return ResponseEntity.ok();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
