package com.example.ecommerce.security;

import com.example.ecommerce.user.UserServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/login", "/health","/api/user/register").permitAll( )
                        .anyRequest().authenticated()).formLogin(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        UserDetails u1 = User.withUsername("suvisk")
                .password(bCryptPasswordEncoder().encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(u1);
    }

    @Bean
    public DaoAuthenticationProvider authProvider(
            UserServices userServices,
            BCryptPasswordEncoder encoder) {

        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(userServices);
        dao.setPasswordEncoder(encoder);

        return dao;
    }
}