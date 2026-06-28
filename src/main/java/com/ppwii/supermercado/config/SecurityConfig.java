package com.ppwii.supermercado.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/home", "/login", "/css/**", "/images/**").permitAll()
                .requestMatchers("/usuario/**").hasAuthority("Admin")
                .requestMatchers("/categoria/create", "/categoria/edit/**", "/categoria/delete/**", "/categoria/save").hasAnyAuthority("Admin", "Gerente")
                .requestMatchers("/categoria/**").authenticated()
                .requestMatchers("/fornecedor/create", "/fornecedor/edit/**", "/fornecedor/delete/**", "/fornecedor/save").hasAnyAuthority("Admin", "Gerente")
                .requestMatchers("/fornecedor/**").authenticated()
                .requestMatchers("/produto/create", "/produto/edit/**", "/produto/delete/**", "/produto/save").hasAnyAuthority("Admin", "Gerente")
                .requestMatchers("/produto/**").authenticated()
                .requestMatchers("/cliente/create", "/cliente/edit/**", "/cliente/delete/**", "/cliente/save").hasAnyAuthority("Admin", "Gerente")
                .requestMatchers("/cliente/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/accessDenied")
            );

        return http.build();
    }
}
