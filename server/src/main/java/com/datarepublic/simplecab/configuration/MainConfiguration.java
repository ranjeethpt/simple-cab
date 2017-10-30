package com.datarepublic.simplecab.configuration;

import com.datarepublic.simplecab.configuration.annotation.AppProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@Configuration
@Import({
        JpaDaoConfiguration.class,
        ServiceConfiguration.class,
        WebConfiguration.class,
        CacheConfiguration.class
})
@AppProperties
public class MainConfiguration {
}
