package SistemaDeViajes.sistema.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService uds) {
        this.userDetailsService = uds;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Públicos
                        .requestMatchers("/login", "/resources/**", "/crearCuenta", "/guardarUsuario")
                        .permitAll()

                        // Solo ROLE_USER puede ver /user-web/**
                        .requestMatchers("/user-web/**")
                        .hasRole("USER")

                        // Endpoints de administración
                        .requestMatchers("/editar/*", "/agregar/**", "/eliminar/*")
                        .hasRole("ADMIN")

                        // La raíz “/” para ADMIN, SECRETARIA, CHOFER
                        .requestMatchers("/")
                        .hasAnyRole("ADMIN", "SECRETARIA", "CHOFER")

                        // Cualquier otro requiere autenticación
                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((req, res, auth) -> {
                            boolean isUser = auth.getAuthorities().stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
                            res.sendRedirect(isUser ? "/user-web/index" : "/");
                        })
                        .permitAll()
                )


                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )

                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/errores/403")
                );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
