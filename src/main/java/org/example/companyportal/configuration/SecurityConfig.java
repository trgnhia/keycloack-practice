package org.example.companyportal.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.example.companyportal.utilities.KeycloakRoleConverter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            KeycloakRoleConverter keycloakRoleConverter
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/me").authenticated()
                        .requestMatchers("/superadmin/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                        .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers("/requests/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )

                /*
                 * Configure application as OAuth2 Resource Server.
                 *
                 * Spring Security will:
                 * 1. Read Bearer JWT token from request
                 * 2. Verify token signature & expiration using Keycloak issuer
                 * 3. Convert Keycloak roles from JWT claims into Spring authorities
                 * 4. Use these authorities for role-based authorization
                 */
                .oauth2ResourceServer(oauth ->
                        oauth.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(
                                        jwtAuthenticationConverter(keycloakRoleConverter)
                                )
                        )
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter (KeycloakRoleConverter keycloakRoleConverter) {
        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(
                keycloakRoleConverter
        );

        return converter;
    }
}
