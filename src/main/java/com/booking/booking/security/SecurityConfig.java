package com.booking.booking.security;

import com.booking.booking.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {


    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public AuthenticationTokenFilter authenticationTokenFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        return new AuthenticationTokenFilter(jwtUtil, customUserDetailsService);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationTokenFilter authenticationTokenFilter) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(e ->
                        e.authenticationEntryPoint(authenticationEntryPoint())
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth",
                                "/api/auth/**"
                        )
                        .permitAll()

                        // Prenotazioni personali
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/bookings/my"
                        )
                        .authenticated()

                        // Lista globale prenotazioni
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/bookings",
                                "/api/bookings/"
                        )
                        .hasRole("ADMIN")

                        // Creazione prenotazione
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/bookings",
                                "/api/bookings/"
                        )
                        .hasAnyRole(
                                "CUSTOMER",
                                "ADMIN"
                        )

                        // Dettaglio/modifica/cancellazione
                        // Ownership controllata nel service
                        .requestMatchers(
                                "/api/bookings",
                                "/api/bookings/**"
                        )
                        .authenticated()

                        // Lettura camere
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/rooms",
                                "/api/rooms/**"
                        )
                        .hasAnyRole(
                                "CUSTOMER",
                                "ADMIN"
                        )

                        // Creazione/modifica/cancellazione camere
                        .requestMatchers(
                                "/api/rooms",
                                "/api/rooms/**"
                        )
                        .hasRole("ADMIN")

                        // Profilo personale
                        .requestMatchers(
                                "/api/users/me"
                        )
                        .authenticated()

                        // Lista utenti
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/users",
                                "/api/users/"
                        )
                        .hasRole("ADMIN")

                        // Gestione utenti
                        .requestMatchers(
                                "/api/users",
                                "/api/users/**"
                        )
                        .hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                );
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
