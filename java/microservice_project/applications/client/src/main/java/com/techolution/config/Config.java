package com.techolution.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tdelesio on 5/25/17.
 */
@Configuration
public class Config {

    @Bean
    @LoadBalanced
    @Qualifier("loadBalancedRestTemplate")
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
