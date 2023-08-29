package com.example.examboard.config;

import com.example.examboard.service.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    PrincipalOauth2UserService principalOauth2UserService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .authorizeHttpRequests((request)-> request
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/articles/lists","/user/**").permitAll()
                .anyRequest().authenticated())

                .formLogin((form)->form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/articles/lists", true)
                        .usernameParameter("userId"))

                .logout(out->out
                        .logoutSuccessUrl("/articles/lists")
                        .logoutUrl("/logout"))

                .oauth2Login(oAuth -> oAuth
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/articles/lists")
                        .userInfoEndpoint(userInfo-> userInfo
                                .userService(principalOauth2UserService)))


                .csrf(csrf -> csrf.disable());
        return http.build();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
