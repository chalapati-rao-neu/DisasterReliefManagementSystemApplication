package com.example.disastermanagement.config;

import com.example.disastermanagement.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
        		.requestMatchers("/home").authenticated()
                // Allow access to static resources
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                // Allow access to login page
                .requestMatchers("/login", "/signup").permitAll()

                // Admin access
                .requestMatchers("/users/**", "/resources/**").hasRole("ADMIN")

                // Relief Coordinator access
                .requestMatchers("/relief-requests/pending-requests").hasRole("RELIEF_COORDINATOR")
                .requestMatchers("/tasks/**").hasRole("RELIEF_COORDINATOR")

                // User access
                .requestMatchers("/relief-requests/create", "/relief-requests/view").hasRole("USER")

                // Volunteer access (for future functionality)
                .requestMatchers("/volunteer/tasks").hasRole("VOLUNTEER")

                // Any other request requires authentication
                .anyRequest().authenticated()
        )
        .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
        )
        .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
