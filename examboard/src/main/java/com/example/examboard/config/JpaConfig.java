package com.example.examboard.config;

import com.example.examboard.entity.UserAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
    @Bean
    public AuditorAware<String> auditorAware(){
        //return ()-> Optional.of("mini");//TODO: 스프링 시큐리티로 인증 기능을 붙이게 될 때 수정.,.,.
        return new AuditorAwareImpl();
    }
}
