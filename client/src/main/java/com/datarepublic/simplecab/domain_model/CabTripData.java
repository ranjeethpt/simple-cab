package com.datarepublic.simplecab.domain_model;

import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * Created by ranjeethpt on 30/10/17.
 *
 * @author ranjeethpt
 */
@Data
public class CabTripData {
    Set<String> medallion;
    Date date;
}
