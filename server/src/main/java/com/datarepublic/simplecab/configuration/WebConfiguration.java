package com.datarepublic.simplecab.configuration;

import com.datarepublic.simplecab.service.CabRetrievalService;
import com.datarepublic.simplecab.web.controller.CabController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@Configuration
public class WebConfiguration {

    @Bean
    public CabController cabController(CabRetrievalService cabRetrievalService) {
        return new CabController(cabRetrievalService);
    }
}
