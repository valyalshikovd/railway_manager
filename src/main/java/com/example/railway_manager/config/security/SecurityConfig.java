package com.example.railway_manager.config.security;

import com.example.railway_manager.service.secure.EndPointRouteService;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private final UserDetailsService userDetailsService;

    private final EndPointRouteService endPointRouteService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {



        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request ->

                        {
                            request

//                                    .requestMatchers(HttpMethod.POST, "/register").permitAll()
//                                    .requestMatchers(HttpMethod.POST, "/login").permitAll()
//                                    .requestMatchers(HttpMethod.POST, "/registerAndLogin").permitAll()
                                    .requestMatchers(ApiPaths.BASE_API_ADMIN_TEST).hasRole("ADMIN");
                            // .anyRequest().authenticated()
                            endPointRouteService.getRoutes().forEach(route -> {
                                if(route.getRoles() == null){
                                    System.out.println(route.getRoute());
                                    request.requestMatchers(route.getMethod(), route.getRoute()).permitAll();
                                }else {
                                    System.out.println(route.getRoute());
                                    route.getRoles().forEach(role -> {
                                        System.out.println(role);
                                        request.requestMatchers(route.getMethod(), route.getRoute()).hasAuthority(role);
                                    });
                                }
                            });

                        }
                )
                .httpBasic(Customizer.withDefaults()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();


    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);


        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }


}