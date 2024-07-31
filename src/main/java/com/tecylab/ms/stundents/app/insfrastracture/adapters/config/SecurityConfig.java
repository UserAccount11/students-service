package com.tecylab.ms.stundents.app.insfrastracture.adapters.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authHttp -> authHttp
        .requestMatchers(HttpMethod.GET, "/authorized").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/students").hasAnyAuthority("SCOPE_read", "SCOPE_write")
        .requestMatchers(HttpMethod.GET, "/api/v1/students/{id}").hasAuthority("SCOPE_write")
        .requestMatchers(HttpMethod.POST, "/api/v1/students").hasAuthority("SCOPE_write")
        .requestMatchers(HttpMethod.PUT, "/api/v1/students/{id}").hasAuthority("SCOPE_write")
        .requestMatchers(HttpMethod.DELETE, "/api/v1/students/{id}").hasAuthority("SCOPE_write")
        .anyRequest().authenticated())
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2Login(login -> login.loginPage("/oauth2/authorization/students-service"))
        .oauth2Client(withDefaults())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));

    return http.build();
  }

}
