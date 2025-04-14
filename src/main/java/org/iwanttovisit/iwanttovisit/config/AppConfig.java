package org.iwanttovisit.iwanttovisit.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ConfigurationPropertiesScan(basePackages = "org.iwanttovisit.iwanttovisit")
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@EnableJpaRepositories(basePackages = "org.iwanttovisit.*.repository")
@EntityScan(basePackages = "org.iwanttovisit.iwanttovisit")
@ComponentScan(basePackages = {
        "org.iwanttovisit.iwanttovisit",
        "io.github.ilyalisov"
})
public class AppConfig {
}
