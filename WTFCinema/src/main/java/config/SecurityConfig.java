package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/").permitAll() // Permitir acceso a la página de inicio
                                .requestMatchers("/admin/**").permitAll() // Permitir acceso a las rutas de admin
                                .anyRequest().authenticated() // Cualquier otra petición requiere autenticación
                )
                .formLogin(formLogin ->
                        formLogin.permitAll() // Permitir el acceso al formulario de inicio de sesión
                )
                .logout(logout ->
                        logout.permitAll() // Permitir el acceso a la función de cerrar sesión
                );
        return http.build();
    }
}
//
//
//
//
//// https://www.baeldung.com/spring-session