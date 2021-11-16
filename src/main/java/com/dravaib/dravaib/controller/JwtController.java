package com.dravaib.dravaib.controller;

import javax.validation.Valid;

import com.dravaib.dravaib.config.JwtTokenUtil;
import com.dravaib.dravaib.model.RoleType;
import com.dravaib.dravaib.model.User;
import com.dravaib.dravaib.model.dto.response.JwtResponse;
import com.dravaib.dravaib.repository.RoleRepository;
import com.dravaib.dravaib.repository.UserRepository;
import com.dravaib.dravaib.model.dto.request.CreateUserRequest;
import com.dravaib.dravaib.model.dto.request.JwtRequest;
import com.dravaib.dravaib.service.JwtUserDetailsService;
import com.dravaib.dravaib.utils.ErrorUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@Validated
@CrossOrigin
@RequestMapping("/api")
@RestController()
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ErrorUtil errorUtil;

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticates a user", tags = { "user" })
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity
                .ok(new JwtResponse(token, userRepository.findByPersonDetailsEmail(userDetails.getUsername()).get()));
    }

    @PostMapping(value = "/register")
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
    @Operation(summary = "Registers a new user", tags = { "user" })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserRequest user) {

        if (userRepository.existsByPersonDetailsEmail(user.getPersonDetails().getEmail())) {
            return errorUtil.createSingleFieldError("email",
                    "Already have a registered account with this e-mail: " + user.getPersonDetails().getEmail());
        }

        User newUser = new User();
        newUser.setPersonDetails(user.getPersonDetails());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setRole(roleRepository.findByRoleType(RoleType.ROLE_GUEST).get());

        userRepository.save(newUser);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}