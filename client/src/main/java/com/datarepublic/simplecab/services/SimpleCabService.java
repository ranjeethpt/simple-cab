package com.datarepublic.simplecab.services;

import com.datarepublic.simplecab.domain_model.CabTripResponse;

import java.util.Date;

public interface SimpleCabService {

    boolean deleteCache();

    CabTripResponse[] getMedallionsSummary(Date pickupDate, String... medallions);

    CabTripResponse[] getMedallionsSummary(Date pickupDate, boolean ignoreCache, String... medallions);

}
