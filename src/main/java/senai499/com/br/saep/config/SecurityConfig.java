package senai499.com.br.saep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            LoginSuccessHandler loginSuccessHandler)
            throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // Páginas públicas
                        .requestMatchers(
                                "/",
                                "/index",
                                "/login",
                                "/sobre",
                                "/servicos",
                                "/cadastro",
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/images/**"
                        ).permitAll()

                        // Áreas restritas por perfil
                        .requestMatchers("/dashboard", "/dashboard/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/cliente/**")
                        .hasRole("CLIENTE")

                        .requestMatchers("/admin/funcionarios/**")
                        .hasAnyRole("ADMIN", "FUNCIONARIO")
                        .requestMatchers("/perfil/alterar-senha")
                        .authenticated()
                        // Demais rotas protegidas
                        .anyRequest().authenticated()
                )

                // Login personalizado
                .formLogin(form -> form

                        .loginPage("/login")

                        .loginProcessingUrl("/login")

                        .usernameParameter("email")

                        .passwordParameter("senha")

                        .failureUrl("/login?erro")

                        .successHandler(loginSuccessHandler)

                        .permitAll()
                )

                // Logout gerenciado pelo Security
                .logout(logout -> logout

                        .logoutUrl("/logout")

                        .logoutSuccessUrl("/login?sair")

                        .invalidateHttpSession(true)

                        .deleteCookies("JSESSIONID")

                        .permitAll()
                );

        return http.build();
    }
}