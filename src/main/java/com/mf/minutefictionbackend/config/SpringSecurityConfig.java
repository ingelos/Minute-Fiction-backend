package com.mf.minutefictionbackend.config;

import com.mf.minutefictionbackend.security.CustomAccessDeniedHandler;
import com.mf.minutefictionbackend.filter.JwtRequestFilter;
import com.mf.minutefictionbackend.security.CustomAuthenticationEntryPoint;
import com.mf.minutefictionbackend.services.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    public final CustomUserDetailService customUserDetailService;
    private final JwtRequestFilter jwtRequestFilter;
    private final PasswordEncoder passwordEncoder;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    public SpringSecurityConfig(CustomUserDetailService customUserDetailService, JwtRequestFilter jwtRequestFilter, PasswordEncoder passwordEncoder, CustomAccessDeniedHandler customAccessDeniedHandler, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customUserDetailService = customUserDetailService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }


    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        //USERS
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/*").hasAuthority("READER")
                        .requestMatchers(HttpMethod.PUT, "/users/*").hasAuthority("READER")
                        .requestMatchers(HttpMethod.DELETE, "/users/*").hasAuthority("READER")
                        .requestMatchers(HttpMethod.POST, "/users/**").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.GET, "/users/**").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.PUT, "/users/**").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("EDITOR")
                        //AUTHOR PROFILES
                        .requestMatchers(HttpMethod.GET, "/authorprofiles/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/authorprofiles/**").hasAnyAuthority("READER", "AUTHOR", "EDITOR")
                        .requestMatchers(HttpMethod.PATCH, "/authorprofiles/**").hasAnyAuthority("AUTHOR", "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/authorprofiles/**").hasAnyAuthority("AUTHOR", "EDITOR")

                        //THEMES
                        .requestMatchers(HttpMethod.GET, "/themes/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/themes").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.PATCH, "/themes/**").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/themes/**").hasAuthority("EDITOR")
                        //STORIES
                        .requestMatchers(HttpMethod.GET, "/stories/published/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/stories/{storyId}/comments").permitAll()

                        .requestMatchers(HttpMethod.POST, "/stories/submit/*").hasAuthority("AUTHOR")
//                        .requestMatchers(HttpMethod.PATCH, "/stories/**").hasAuthority( "EDITOR")
//                        .requestMatchers(HttpMethod.GET, "/stories/declined").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.GET, "/stories/**").hasAuthority( "EDITOR")
                        .requestMatchers(HttpMethod.PATCH, "/stories/**").hasAuthority( "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/stories/**").hasAnyAuthority("AUTHOR", "EDITOR")

                        //COMMENTS
                        .requestMatchers(HttpMethod.GET, "/comments/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/stories/{storyId}/comments").hasAnyAuthority("READER", "EDITOR")
                        .requestMatchers(HttpMethod.PATCH, "/comments/**").hasAnyAuthority("READER", "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/comments/**").hasAnyAuthority("READER", "EDITOR")
                        //MAILINGS
                        .requestMatchers(HttpMethod.GET, "/mailings/**").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.POST, "/mailings/**").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.PATCH, "/mailings/**").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/mailings/**").hasAuthority("EDITOR")

                        .requestMatchers("/authenticated").authenticated()
                        .requestMatchers("/authenticate").permitAll()
                        .anyRequest().denyAll()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedHandler(customAccessDeniedHandler)
                                .authenticationEntryPoint(customAuthenticationEntryPoint))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }


}
