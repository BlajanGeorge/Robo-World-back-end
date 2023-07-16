package com.roboworldbackend.security;

import com.roboworldbackend.db.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.roboworldbackend.utils.Constants.*;

/**
 * Security configuration class
 *
 * @author Blajan George
 */
@org.springframework.context.annotation.Configuration
public class ApplicationSecurity {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthorizationFilter authorizationFilter, FilterChainExceptionHandler exceptionHandler) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/login").permitAll()
                .requestMatchers(HttpMethod.POST, USER_PATH).permitAll()
                .requestMatchers("/refresh-token").permitAll()
                .requestMatchers("/test/message").permitAll()
                .requestMatchers("/chat").permitAll()
                .requestMatchers(USER_ID_PATH + "/change-password").hasRole(Role.CUSTOMER.name())
                .requestMatchers(HttpMethod.GET, USER_ID_PATH).hasAnyRole(Role.ADMIN.name(), Role.CUSTOMER.name())
                .requestMatchers(HttpMethod.PUT, USER_PATH).hasAnyRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, USER_PATH).hasAnyRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.GET, USER_ID_PATH + "/conversations").hasAnyRole(Role.ADMIN.name(), Role.CUSTOMER.name())
                .requestMatchers(FRIENDS_PATH + "/**").hasAnyRole(Role.ADMIN.name(), Role.CUSTOMER.name())
                .requestMatchers(FRIEND_REQUESTS_PATH + "/**").hasAnyRole(Role.ADMIN.name(), Role.CUSTOMER.name())
                .requestMatchers(BOTS_PATH + "/**").hasAnyRole(Role.ADMIN.name(), Role.CUSTOMER.name())
                .and()
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandler, JwtAuthorizationFilter.class);

        return http.build();
    }
}
