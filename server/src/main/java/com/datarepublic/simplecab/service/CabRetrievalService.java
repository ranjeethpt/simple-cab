package com.datarepublic.simplecab.service;

import com.datarepublic.simplecab.domain_model.CabTripResponseDTO;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
public interface CabRetrievalService {
    List<CabTripResponseDTO> retrieveCabTrip(Set<String> medallions, Date pickupDate, Boolean ignoreCache);
    String clearCache();
}
