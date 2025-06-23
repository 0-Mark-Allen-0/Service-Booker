package com.example.ServiceBooker.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // Assuming you have a UserRepository. This needs to be created.
    // private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // User user = userRepository.findByEmail(email)
        //         .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // This is a placeholder. You'll need to fetch the user from the database.
        // For now, we'll return a hardcoded user for compilation.
        // In the full implementation, the commented-out code above would be used.
        return org.springframework.security.core.userdetails.User
                .withUsername("test@example.com")
                .password("password")
                .authorities("USER")
                .build();
    }
}