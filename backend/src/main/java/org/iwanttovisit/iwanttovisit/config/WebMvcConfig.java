package org.iwanttovisit.iwanttovisit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(
            final CorsRegistry registry
    ) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://localhost:81",
                        "https://iwanttovisit.org"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

}
