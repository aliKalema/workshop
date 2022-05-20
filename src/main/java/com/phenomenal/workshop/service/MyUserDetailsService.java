package com.phenomenal.workshop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.phenomenal.workshop.entity.User;
import com.phenomenal.workshop.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		user.orElseThrow(()->new UsernameNotFoundException("User: "+username+" NOT FOUND!"));
		return user.map(MyUserDetails::new).get();
	}

}
