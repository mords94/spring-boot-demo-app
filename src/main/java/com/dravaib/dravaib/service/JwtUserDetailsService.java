package com.dravaib.dravaib.service;

import java.util.ArrayList;
import java.util.Optional;

import com.dravaib.dravaib.model.User;
import com.dravaib.dravaib.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByPersonDetailsEmail(email);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
		return new org.springframework.security.core.userdetails.User(user.get().getPersonDetails().getEmail(),
				user.get().getPassword(), new ArrayList<>());
	}
}