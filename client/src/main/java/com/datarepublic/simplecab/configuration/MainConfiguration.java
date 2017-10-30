package com.datarepublic.simplecab.configuration;

import com.datarepublic.simplecab.services.SimpleCabService;
import com.datarepublic.simplecab.services.SimpleCabServiceImpl;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by ranjeethpt on 30/10/17.
 *
 * @author ranjeethpt
 */
@Configuration
@PropertySource("classpath:configuration-default.properties")
public class MainConfiguration implements EnvironmentAware {

    private Environment env;

    public static final String SIMPLE_SERVICE_BEAN_NAME = "simpleCabService";

    @Bean(name = SIMPLE_SERVICE_BEAN_NAME)
    public SimpleCabService simpleCabService(RestTemplate restTemplate) {
        return new SimpleCabServiceImpl(restTemplate, env.getProperty("server.baseurl"));
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(env.getProperty("server.timeout.read", Integer.class));
        factory.setConnectTimeout(env.getProperty("server.timeout.connect", Integer.class));
        return new RestTemplate(factory);
    }

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }
}
