package com.datarepublic.simplecab.dao.repository;

import java.util.Date;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
public interface CabDao {

    Integer getCountByMedallionAndPickupDatetime(String medallionId, Date pickupDate);

    void cleanCache(String medallionId, Date pickupDate);

    String clearCache();
}
