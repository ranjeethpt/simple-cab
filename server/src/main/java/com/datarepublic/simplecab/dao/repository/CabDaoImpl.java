package com.datarepublic.simplecab.dao.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.Date;

import static com.datarepublic.simplecab.configuration.CacheConfiguration.CAB_TRIP_CACHE_NAME;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@CacheConfig(cacheNames = CAB_TRIP_CACHE_NAME)
public class CabDaoImpl implements CabDao {
    private final SimpleCabRepository simpleCabRepository;

    public CabDaoImpl(SimpleCabRepository simpleCabRepository) {
        checkNotNull(simpleCabRepository);

        this.simpleCabRepository = simpleCabRepository;
    }

    @Override
    @Cacheable(keyGenerator = "cabTripKeyGenerator")
    public Integer getCountByMedallionAndPickupDatetime(String medallionId, Date pickupDate) {
        return simpleCabRepository.countById_MedallionAndId_PickupDatetime(medallionId, pickupDate);
    }

    @Override
    @CacheEvict(keyGenerator = "cabTripKeyGenerator")
    public void cleanCache(String medallionId, Date pickupDate) {
    }

    @Override
    @CacheEvict(allEntries = true)
    public String clearCache() {
        return "SUCCESS";
    }
}
