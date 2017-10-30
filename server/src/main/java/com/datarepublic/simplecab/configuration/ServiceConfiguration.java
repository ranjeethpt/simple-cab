package com.datarepublic.simplecab.configuration;

import com.datarepublic.simplecab.dao.repository.CabDao;
import com.datarepublic.simplecab.dao.repository.CabDaoImpl;
import com.datarepublic.simplecab.dao.repository.SimpleCabRepository;
import com.datarepublic.simplecab.service.CabRetrievalService;
import com.datarepublic.simplecab.service.CabRetrievalServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@Configuration
public class ServiceConfiguration {

    @Bean
    public CabDao cabDao(SimpleCabRepository simpleCabRepository) {
        return new CabDaoImpl(simpleCabRepository);
    }

    @Bean
    public CabRetrievalService cabRetrievalService(CabDao cabDao) {
        return new CabRetrievalServiceImpl(cabDao);
    }

}
