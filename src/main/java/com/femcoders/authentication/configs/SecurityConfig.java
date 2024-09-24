package com.femcoders.authentication.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.cors(cors -> cors.disable())
                                .csrf(csrf -> csrf.disable()).authorizeHttpRequests(authz -> authz
                                                .requestMatchers("/auth/login", "/auth/register", "/resources/**",
                                                                "/static/**",
                                                                "/css/**", "/js/**")
                                                .permitAll() // Permitir acceso a rutas de login y registro sin
                                                             // autenticación
                                                .requestMatchers("/admin/**").hasAuthority("SUPER_ADMIN")
                                                .requestMatchers("/group/**")
                                                .hasAnyAuthority("CREATE_GROUP", "UPDATE_GROUP", "DELETE_GROUP")
                                                .requestMatchers("/group/view/**")
                                                .hasAnyAuthority("READ_GROUP", "JOIN_GROUP")
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login") // Ruta a la página personalizada de login
                                                .permitAll()) // Permitir acceso a todos a la página de login
                                .logout(logout -> logout
                                                .permitAll());

                return http.build();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(List.of("http://localhost:5173"));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

                source.registerCorsConfiguration("/**", configuration);

                return source;
        }

        @Bean
        public UserDetailsService userDetailsService(DataSource dataSource) {
                // Configuramos el JdbcUserDetailsManager para que consulte los usuarios, roles
                // y permisos desde las tablas correspondientes
                JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

                // Consulta SQL para obtener el usuario y su contraseña
                jdbcUserDetailsManager.setUsersByUsernameQuery(
                                "SELECT name, password, enabled FROM users WHERE name = ?");

                // Consulta SQL para obtener los roles del usuario
                jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                                "SELECT u.name, p.permission_name " +
                                                "FROM users u " +
                                                "JOIN user_group_role ugr ON u.id = ugr.user_id " +
                                                "JOIN roles r ON ugr.role_id = r.id " +
                                                "JOIN role_permissions rp ON r.id = rp.role_id " +
                                                "JOIN permissions p ON rp.permission_id = p.id " +
                                                "WHERE u.name = ?");

                return jdbcUserDetailsManager;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
