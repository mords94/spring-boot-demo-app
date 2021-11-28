package com.dravaib.dravaib.service;

import javax.servlet.http.HttpServletRequest;

import com.dravaib.dravaib.config.JwtTokenUtil;
import com.dravaib.dravaib.model.RoleType;
import com.dravaib.dravaib.model.User;
import com.dravaib.dravaib.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component("authorizationService")
public class AuthorizationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    HttpServletRequest request;

    public User getUser() {
        String token = request.getHeader("Authorization");
        String email = jwtTokenUtil.getUsernameFromToken(token.replace("Bearer ", ""));
        return userRepository.findByPersonDetailsEmail(email).get();
    }

    public boolean isAdmin() {
        if (!getUser().hasRole(RoleType.ROLE_ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return true;
    }

    public boolean isOwnerOrAdmin() {
        if (getUser().hasRole(new RoleType[] { RoleType.ROLE_ADMIN, RoleType.ROLE_OWNER })) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return true;
    }
}
