package com.auth.config;

import com.auth.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resource/**",
            "/swagger-resources"
    };

    @Autowired
    private JwtAuthFilter authFilter;

    @Bean //Authentication
    public UserDetailsService userDetailsService(){
//        UserDetails admin = User.withUsername("Rakesh")
//                .password(encoder.encode("rk1"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.withUsername("John")
//                .password(encoder.encode("rk2"))
//                .roles("USER")
//                .build();

        return new UserDetailService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity.csrf().disable()
               .authorizeHttpRequests()
               .requestMatchers("/auth/register", "/auth/authenticate", "/products/welcome").permitAll()
               .and()
               .authorizeHttpRequests().requestMatchers("/products/**")
               .authenticated().and()
               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .authenticationProvider(authenticationProvider())
               .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
