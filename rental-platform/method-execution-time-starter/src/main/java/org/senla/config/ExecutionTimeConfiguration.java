package org.senla.config;

import org.senla.aop.ExecutionTimeAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutionTimeConfiguration {

    @Bean
    public ExecutionTimeAspect executionTimeAspect () {
        return new ExecutionTimeAspect();
    }
}
