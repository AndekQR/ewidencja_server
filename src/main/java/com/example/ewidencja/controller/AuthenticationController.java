package com.example.ewidencja.controller;

import com.example.ewidencja.helper.AuthorityType;
import com.example.ewidencja.helper.UserAlreadyInDatabaseException;
import com.example.ewidencja.model.ConfirmationToken;
import com.example.ewidencja.model.User;
import com.example.ewidencja.repository.ConfirmationTokenRepository;
import com.example.ewidencja.security.AuthenticationRequest;
import com.example.ewidencja.security.jwt.JwtTokenProvider;
import com.example.ewidencja.service.interfaces.AuthorityService;
import com.example.ewidencja.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private AuthorityService authorityService;
    private ConfirmationTokenRepository confirmationTokenRepository;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, AuthorityService authorityService, ConfirmationTokenRepository confirmationTokenRepository) {
        this.authenticationManager=authenticationManager;
        this.jwtTokenProvider=jwtTokenProvider;
        this.userService=userService;
        this.authorityService=authorityService;
        this.confirmationTokenRepository=confirmationTokenRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> login(@RequestBody AuthenticationRequest data) {
        String username = data.getUsername();
        User user = userService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username "+username+" not found"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
        String token = jwtTokenProvider.createToken(username, user.getAuthoritiesList());

        HashMap<Object, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", user);
        return ResponseEntity.ok(model);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<Object, Object>> register(@RequestBody User user) throws UserAlreadyInDatabaseException {
        String _password = user.getPassword();
        if (userService.findByEmail(user.getEmail()).orElse(null) == null) {
            user.setAuthorities(authorityService.createOrGetAuthorities(new AuthorityType[]{AuthorityType.ROLE_USER}));
            user.setVerified(true);
            userService.save(user);
        } else {
            throw new UserAlreadyInDatabaseException("User already in database");
        }

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        return login(new AuthenticationRequest(user.getUsername(), _password));
    }
}
