package com.vedang.jobpilot_ai.util;

import com.vedang.jobpilot_ai.entity.User;
import com.vedang.jobpilot_ai.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private final UserRepository userRepository;

    public AuthUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        // Spring Security stored the User object here in JwtFilter
        // We're just retrieving it now
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
