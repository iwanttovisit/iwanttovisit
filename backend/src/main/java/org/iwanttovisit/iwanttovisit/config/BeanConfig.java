package org.iwanttovisit.iwanttovisit.config;

import io.github.ilyalisov.jwt.service.TokenService;
import io.github.ilyalisov.jwt.service.TokenServiceImpl;
import lombok.RequiredArgsConstructor;
import org.iwanttovisit.iwanttovisit.web.security.JwtProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    private final JwtProperties jwtProperties;

    @Bean
    public TokenService tokenService() {
        return new TokenServiceImpl(
                jwtProperties.getSecret()
        );
    }

}
