package com.datarepublic.simplecab.service;

import com.datarepublic.simplecab.dao.repository.CabDao;
import com.datarepublic.simplecab.domain_model.CabTripResponseDTO;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
public class CabRetrievalServiceImpl implements CabRetrievalService {

    private final CabDao cabDao;

    public CabRetrievalServiceImpl(CabDao cabDao) {
        checkNotNull(cabDao);

        this.cabDao = cabDao;
    }

    @Override
    public List<CabTripResponseDTO> retrieveCabTrip(Set<String> medallions, Date pickupDate, Boolean ignoreCache) {
        checkNotNull(medallions, " Medallions cannot be null");
        checkNotNull(pickupDate, "pickupDate cannot be empty");

        List<CabTripResponseDTO> cabTripResponseDTOS = newArrayList();
        medallions.forEach(medallion -> {
                    if (ignoreCache) {
                        cabDao.cleanCache(medallion, pickupDate);
                    }
                    cabTripResponseDTOS.add(CabTripResponseDTO.builder().
                            medallion(medallion).
                            count(cabDao.getCountByMedallionAndPickupDatetime(medallion, pickupDate))
                            .build());
                }
        );
        return cabTripResponseDTOS;
    }

    @Override
    public String clearCache() {
        return cabDao.clearCache();
    }
}
