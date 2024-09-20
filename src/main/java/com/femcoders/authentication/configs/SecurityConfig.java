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

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(authz -> authz
                                                .requestMatchers("/admin/**").hasAuthority("WRITE") // Solo quienes
                                                                                                    // tienen permiso
                                                                                                    // WRITE pueden
                                                                                                    // acceder
                                                .requestMatchers("/user/**").hasAuthority("READ") // Solo quienes tienen
                                                                                                  // permiso READ pueden
                                                                                                  // acceder
                                                .anyRequest().authenticated() // El resto de las rutas deben estar
                                                                              // autenticadas
                                )
                                .formLogin(form -> form
                                                .permitAll() // Permite que el formulario de login sea accesible para
                                                             // todos
                                );

                return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService(DataSource dataSource) {
                // Configuramos el JdbcUserDetailsManager para que consulte los usuarios, roles
                // y permisos desde las tablas correspondientes
                JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

                // Consulta SQL para obtener el usuario y su contraseña
                jdbcUserDetailsManager.setUsersByUsernameQuery(
                                "SELECT username, password, enabled FROM users WHERE username = ?");

                // Consulta SQL para obtener los roles del usuario
                jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                                "SELECT u.username, p.permission_name " +
                                                "FROM users u " +
                                                "JOIN user_group_role ugr ON u.id = ugr.user_id " +
                                                "JOIN roles r ON ugr.role_id = r.id " +
                                                "JOIN role_permissions rp ON r.id = rp.role_id " +
                                                "JOIN permissions p ON rp.permission_id = p.id " +
                                                "WHERE u.username = ?");

                return jdbcUserDetailsManager;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
