package org.example.tablereservation.config;

import jakarta.servlet.http.HttpServletResponse;
import org.example.tablereservation.repository.UserRepository;
import org.example.tablereservation.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)// 일반적으로 운영 환경에서는 CSRF 보호를 켜두어야 하지만, 필요에 따라 비활성화
                        .authorizeHttpRequests(authorize -> authorize
//                                .requestMatchers("/users/register").permitAll()
//                                .requestMatchers("/stores/register").permitAll()
//                                .requestMatchers("/login").permitAll()
//                                .anyRequest().authenticated()
                                .requestMatchers("/**").permitAll()
                        )
                        .formLogin(formLogin -> formLogin
                                .loginProcessingUrl("/login")
                                .usernameParameter("id")
                                .passwordParameter("password")
                                .successHandler((request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK))
                                .failureHandler((request, response, exception) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                        )
                        .logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK))
                                .permitAll()
                        );
        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);

    }
}
