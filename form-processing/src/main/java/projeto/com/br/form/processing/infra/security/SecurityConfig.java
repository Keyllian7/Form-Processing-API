package projeto.com.br.form.processing.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Autowired
    CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        //Swagger e Auth
                        .requestMatchers(HttpMethod.GET, "/swagger-ui.html/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        //User Controller
                        .requestMatchers(HttpMethod.PUT, "user/update/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "user/list/{id}", "user/list/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "user/forms/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "user/delete/{id}").hasRole("USER")
                        //Form Controller
                        .requestMatchers(HttpMethod.POST, "form/create").authenticated()
                        .requestMatchers(HttpMethod.PUT, "form/update/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "form/list/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "form/delete/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "form/list/all").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
