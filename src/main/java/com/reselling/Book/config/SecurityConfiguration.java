package com.reselling.Book.config;

import com.reselling.Book.service.customerService.MyUserDetailsService;
import com.reselling.Book.service.sellerService.MySellerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private  JwtFilter jwtFilter;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private MySellerDetailsService sellerDetailsService;

    //CUSTOMIZE THE SECURITY CONFIG..................
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for Postman testing
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/register", "/user/login" ,"/seller/register"
                        ,"/seller/login").permitAll() // allow registration without login
                        .anyRequest().authenticated() // everything else requires login
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(userAuthProvider())
                .authenticationProvider(sellerAuthProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //----------AUTHENTICATION PROVIDER-----------------------
    @Bean
    public AuthenticationProvider userAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Bean
    public AuthenticationProvider sellerAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(sellerDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    // Used to manage the user authentication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){

        return new ProviderManager(
                List.of(
                        userAuthProvider(),
                        sellerAuthProvider()
                )
        );
    }
}
