package com.io.mountblue.blogapplication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/", "/signup", "/saveUser", "/filter/{postId}","/post/{postId}").permitAll()
                        .requestMatchers("/**").hasAnyRole("ADMIN", "AUTHOR")
                        .anyRequest().authenticated()
        ).formLogin(form ->
                form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
        )
        .logout(logout -> logout.permitAll()
        )
        .exceptionHandling(configurer ->
                configurer.accessDeniedPage("/access-denied")
        );
        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery(
                "select  username, password, enabled from users where username =?"
        );
        userDetailsManager.setAuthoritiesByUsernameQuery(
                "select username, role from roles where username =?"
        );
        return userDetailsManager;
    }

}
