package org.iwanttovisit.iwanttovisit.web.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private Duration access;
    private Duration refresh;
    private Duration activation;
    private Duration reset;

}
