package com.datarepublic.simplecab.dao.repository;

import com.datarepublic.simplecab.dao.entity.CabTripData;
import com.datarepublic.simplecab.dao.entity.CabTripDataCompositeKey;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.TemporalType;
import java.util.Date;

public interface SimpleCabRepository extends CrudRepository<CabTripData, CabTripDataCompositeKey> {
    Integer countById_MedallionAndId_PickupDatetime(String medallionId, @Temporal(TemporalType.DATE) Date pickupDate);
}
