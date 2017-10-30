package com.datarepublic.simplecab.configuration;

import com.datarepublic.simplecab.service.CabRetrievalService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@Configuration
@EnableAutoConfiguration
public class TestContextConfiguration {

    @Bean
    public CabRetrievalService cabRetrievalService() {
        return mock(CabRetrievalService.class);
    }
}
