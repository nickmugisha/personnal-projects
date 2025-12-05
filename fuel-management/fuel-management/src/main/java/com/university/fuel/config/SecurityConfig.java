// src/main/java/com/university/fuel/config/SecurityConfig.java
package com.university.fuel.config;

import com.university.fuel.security.CustomUserDetailsService;
import com.university.fuel.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/users/roles").permitAll()
                .requestMatchers("/api/carburants").permitAll()
                .requestMatchers("/api/carburants/**").permitAll()
                .requestMatchers("/api/engins").hasAnyRole("ADMIN", "RESPONSABLE_CARBURANT")
                .requestMatchers("/api/engins/**").hasAnyRole("ADMIN", "RESPONSABLE_CARBURANT")
                .requestMatchers("/api/demandes/mes-demandes").hasRole("CHAUFFEUR")
                .requestMatchers("/api/demandes/en-attente-chef").hasRole("CHEF_CHALOI")
                .requestMatchers("/api/demandes/en-attente-responsable").hasRole("RESPONSABLE_CARBURANT")
                .requestMatchers("/api/demandes/en-attente-daf").hasRole("DAF")
                .requestMatchers("/api/stocks/**").hasAnyRole("RESPONSABLE_CARBURANT", "DAF")
                .requestMatchers("/api/approvisionnements/**").hasRole("RESPONSABLE_CARBURANT")
                .requestMatchers("/api/rapports/**").hasAnyRole("ADMIN", "DAF")
                .requestMatchers("/api/users/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            );
        
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}