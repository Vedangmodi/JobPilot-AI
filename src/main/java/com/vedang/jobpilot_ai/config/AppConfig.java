package com.vedang.jobpilot_ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}


//Spring uses this to hash passwords.
//Why a separate file? You can't put this @Bean inside SecurityConfig yet (that doesn't exist),
// and you can't put it in AuthServiceImpl because service classes aren't @Configuration.
// This is the clean place for it.