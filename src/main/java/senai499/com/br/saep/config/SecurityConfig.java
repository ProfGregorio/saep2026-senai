package senai499.com.br.saep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) {

        http
                .authorizeHttpRequests(auth -> auth

                        // Páginas públicas
                        .requestMatchers(
                                "/",
                                "/index",
                                "/sobre",
                                "/login",
                                "/cadastro"
                        ).permitAll()

                        // Arquivos estáticos
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/images/**",
                                "/webjars/**"
                        ).permitAll()

                        // Demais páginas exigem autenticação
                        .anyRequest().authenticated()
                )

                // Login padrão do Spring Security
                .formLogin(form -> form
                        .permitAll()
                );

        return http.build();
    }
}