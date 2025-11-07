package com.lab.piece_de_monnaie.config;

import com.lab.piece_de_monnaie.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfiguration {

    private final UsuarioRepository usuarioRepository;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByUsername(username)
                .orElseGet(() -> {
                    log.info("{} não encontrado no sistema", username);
                    throw new UsernameNotFoundException("Usuário '" + username + "' não encontrado.");
                });
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }
}
