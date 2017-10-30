package com.datarepublic.simplecab.domain_model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CabTripResponseDTO {
    private String medallion;
    private Integer count;
}
