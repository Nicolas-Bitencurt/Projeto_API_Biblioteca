package com.biblioteca.api.infrastructure.config;

import com.biblioteca.api.infrastructure.security.CustomUserDetailsService;
import com.biblioteca.api.infrastructure.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
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
@EnableMethodSecurity(securedEnabled = true) // Habilita segurança em nível de método
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    // Construtor: Spring injeta os filtros e o UserDetailsService
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    // Bean para o Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean para o Gerenciador de Autenticação (Necessário para a rota /auth/login)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Bean do Provedor de Autenticação (Conecta o UserDetailsService e o Encoder)
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF
                .exceptionHandling(e -> {}) // Mantém o exceptionHandling
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authz ->
                        authz
                                // CORREÇÃO DO 403: Libera a rota /api/auth/** para login
                                .requestMatchers("/api/auth/**").permitAll()

                                // Libera rotas do Swagger (Documentação)
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                                // Rotas de consulta GET são públicas
                                .requestMatchers(HttpMethod.GET, "/api/livros/**", "/api/autores/**", "/api/categorias/**").permitAll()

                                // Exemplo de Restrição por Papel (para o requisito da faculdade):
                                // .requestMatchers(HttpMethod.DELETE, "/api/livros/**").hasRole("ADMIN")

                                // O restante das requisições (POST, PUT, DELETE, outros) exige token
                                .anyRequest().authenticated()
                )

                // Conecta o provedor de autenticação
                .authenticationProvider(authenticationProvider())

                // Adiciona o filtro JWT antes do filtro padrão de login/usuário
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}