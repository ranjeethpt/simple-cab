package com.datarepublic.simplecab.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CabTripDataCompositeKey implements Serializable {
    private String medallion;

    @Column(name = "hack_license")
    private String hackLicense;

    @Temporal(TemporalType.DATE)
    @Column(name = "pickup_datetime")
    private Date pickupDatetime;

    @Temporal(TemporalType.DATE)
    @Column(name = "dropoff_datetime")
    private Date dropOffDateTime;
}
