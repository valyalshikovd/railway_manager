package com.example.railway_manager.config.security;

import com.example.railway_manager.service.secure.EndPointRouteService;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
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
                .cors(
                        configurer -> {
                            // Источник конфигураций CORS
                            var corsConfigurationSource = new UrlBasedCorsConfigurationSource();
                            // Конфигурация CORS
                            var globalCorsConfiguration = new CorsConfiguration();

                            // Разрешаются CORS-запросы:
                            // - с сайта http://localhost:8080
                            globalCorsConfiguration.addAllowedOrigin("*");
                            // - с нестандартными заголовками Authorization и X-CUSTOM-HEADER
                            globalCorsConfiguration.addAllowedHeader("*");
                            globalCorsConfiguration.addAllowedMethod("*");
                            
                            // - с методами GET, POST, PUT, PATCH и DELETE
                            globalCorsConfiguration.setAllowedMethods(List.of(
                                    HttpMethod.GET.name(),
                                    HttpMethod.POST.name(),
                                    HttpMethod.PUT.name(),
                                    HttpMethod.PATCH.name(),
                                    HttpMethod.DELETE.name()
                            ));
                            // JavaScript может обращаться к заголовку X-OTHER-CUSTOM-HEADER ответа
                            globalCorsConfiguration.setExposedHeaders(List.of("X-OTHER-CUSTOM-HEADER"));
                            // Браузер может кешировать настройки CORS на 10 секунд
                            globalCorsConfiguration.setMaxAge(Duration.ofSeconds(10));

                            // Использование конфигурации CORS для всех запросов
                            corsConfigurationSource.registerCorsConfiguration("/**", globalCorsConfiguration);

                            configurer.configurationSource(corsConfigurationSource);
                        })
                            .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request ->

                        {
                            request

                                    .requestMatchers(HttpMethod.POST, ApiPaths.BASE_API + "/*").permitAll()
                                    .requestMatchers(HttpMethod.GET, ApiPaths.BASE_API + "/*").permitAll()
                                    .requestMatchers(HttpMethod.POST, ApiPaths.USER_API + "/*").hasAnyAuthority("USER", "ADMIN")
                                    .requestMatchers(HttpMethod.GET, ApiPaths.ADMIN_API + "/*").hasAuthority("ADMIN")
                                    .requestMatchers(HttpMethod.POST, ApiPaths.ADMIN_API + "/*").hasAuthority("ADMIN");
//                                    .requestMatchers(HttpMethod.POST, "/login").permitAll()
//                                    .requestMatchers(HttpMethod.POST, "/registerAndLogin").permitAll()
                            // .anyRequest().authenticated()
//                            endPointRouteService.getRoutes().forEach(route -> {
//                                if(route.getRoles() == null){
//                                    System.out.println(route.getRoute());
//                                    request.requestMatchers(route.getMethod(), route.getRoute()).permitAll();
//                                }else {
//                                    System.out.println(route.getRoute());
//                                    route.getRoles().forEach(role -> {
//                                        System.out.println(role);
//                                        request.requestMatchers(route.getMethod(), route.getRoute()).hasAuthority(role);
//                                    });
//                                }
//                            });

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