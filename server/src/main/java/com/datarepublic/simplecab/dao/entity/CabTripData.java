package com.datarepublic.simplecab.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cab_trip_data")
public class CabTripData implements Serializable {

    @EmbeddedId
    private CabTripDataCompositeKey id;

    @Column(name = "vendor_id")
    private String vendorId;

    @Column(name = "rate_code")
    private Integer rateCode;

    @Column(name = "store_and_fwd_flag")
    private String storeAndFwdFlag;

    @Column(name = "passenger_count")
    private Integer passengerCount;

    @Column(name = "trip_time_in_secs")
    private Integer tripTimeInSecs;

    @Column(name = "trip_distance")
    private Double tripDistance;
}
