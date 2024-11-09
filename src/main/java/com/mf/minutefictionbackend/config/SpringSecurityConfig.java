package com.mf.minutefictionbackend.config;

import com.mf.minutefictionbackend.security.CustomAccessDeniedHandler;
import com.mf.minutefictionbackend.filter.JwtRequestFilter;
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
import org.springframework.security.web.AuthenticationEntryPoint;
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
    private final AuthenticationEntryPoint customAuthenticationEntryPoint;


    public SpringSecurityConfig(CustomUserDetailService customUserDetailService, JwtRequestFilter jwtRequestFilter, PasswordEncoder passwordEncoder, CustomAccessDeniedHandler customAccessDeniedHandler, AuthenticationEntryPoint customAuthenticationEntryPoint) {
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
                        .requestMatchers(HttpMethod.GET, "/users").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.GET, "/users/{username}").hasAnyAuthority("READER", "EDITOR")
                        .requestMatchers(HttpMethod.PUT, "/users/{username}").hasAuthority("READER")
                        .requestMatchers(HttpMethod.PATCH, "/users/{username}/password").hasAnyAuthority("READER", "AUTHOR", "EDITOR")
                        .requestMatchers(HttpMethod.PATCH, "/users/{username}/email").hasAnyAuthority("READER", "AUTHOR", "EDITOR")
                        .requestMatchers(HttpMethod.PATCH, "/users/{username}/subscription").hasAnyAuthority("READER", "AUTHOR", "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasAnyAuthority("READER", "EDITOR")

                        .requestMatchers(HttpMethod.POST, "/users/{username}/authorities").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.GET, "/users/{username}/authorities").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/users/{username}/authorities/{authority}").hasAuthority("EDITOR")

                        //AUTHOR PROFILES
                        .requestMatchers(HttpMethod.GET, "/authorprofiles").permitAll()
                        .requestMatchers(HttpMethod.GET, "/authorprofiles/{username}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/authorprofiles/{username}/published").permitAll()
                        .requestMatchers(HttpMethod.GET, "/authorprofiles/{username}/photo").permitAll()

                        .requestMatchers(HttpMethod.POST, "/authorprofiles/{username}").hasAnyAuthority("READER", "AUTHOR", "EDITOR")
                        .requestMatchers(HttpMethod.PUT, "/authorprofiles/{username}").hasAnyAuthority("AUTHOR", "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/authorprofiles/{username}").hasAnyAuthority("AUTHOR", "EDITOR")
                        .requestMatchers(HttpMethod.GET, "/authorprofiles/{username}/overview").hasAnyAuthority("AUTHOR", "EDITOR")
                        .requestMatchers(HttpMethod.GET, "/authorprofiles/{username}/unpublished").hasAnyAuthority("AUTHOR", "EDITOR")
                        .requestMatchers(HttpMethod.GET, "/authorprofiles/{username}/stories/download").hasAuthority("AUTHOR")

                        .requestMatchers(HttpMethod.POST, "/authorprofiles/{username}/photo").hasAnyAuthority("AUTHOR", "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/authorprofiles/{username}/photo").hasAnyAuthority("AUTHOR", "EDITOR")

                        //THEMES
                        .requestMatchers(HttpMethod.GET, "/themes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/themes/{themeName}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/themes").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.PUT, "/themes/{themeId}").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/themes/{themeId}").hasAuthority("EDITOR")

                        //STORIES
                        .requestMatchers(HttpMethod.GET, "/stories/published").permitAll()
                        .requestMatchers(HttpMethod.GET, "/stories/published/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/stories/published/{storyId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/stories/published/themes/{themeName}").permitAll()

                        .requestMatchers(HttpMethod.POST, "/stories/submit/{themeId}").hasAnyAuthority("AUTHOR", "EDITOR")


                        .requestMatchers(HttpMethod.GET, "/stories/editor/submitted").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.GET, "/stories/editor/submitted/theme/{themeId}").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.GET, "/stories/editor/{themeId}/stories").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.GET, "/stories/{storyId}").hasAnyAuthority("AUTHOR", "EDITOR")
                        .requestMatchers(HttpMethod.GET, "/stories/{storyId}/comments").permitAll()

                        .requestMatchers(HttpMethod.PUT, "/stories/editor/{storyId}/update").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.PATCH, "/stories/editor/{storyId}/*").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.PATCH, "/stories/editor/themes/{themeId}/publish").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/stories/{storyId}").hasAnyAuthority("AUTHOR", "EDITOR")

                        //COMMENTS
                        .requestMatchers(HttpMethod.GET, "/comments/{commentId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/stories/{storyId}/comments").permitAll()
                        .requestMatchers(HttpMethod.POST, "/stories/{storyId}/comments").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/comments/{commentId}").hasAnyAuthority("READER", "AUTHOR", "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/comments/{commentId}").hasAnyAuthority("READER", "EDITOR")

                        //MAILINGS
                        .requestMatchers(HttpMethod.GET, "/mailings").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.GET, "/mailings/*").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.POST, "/mailings").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.POST, "/mailings/{mailingId}/send").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.PUT, "/mailings/{mailingId}").hasAuthority("EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/mailings/{mailingId}").hasAuthority("EDITOR")

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
