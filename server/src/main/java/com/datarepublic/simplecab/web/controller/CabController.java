package com.datarepublic.simplecab.web.controller;

import com.datarepublic.simplecab.domain_model.CabTripResponseDTO;
import com.datarepublic.simplecab.service.CabRetrievalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@RestController
@RequestMapping("/services")
public class CabController {

    private final CabRetrievalService cabRetrievalService;
    private final DateFormat formatter;

    public CabController(CabRetrievalService cabRetrievalService) {
        checkNotNull(cabRetrievalService);

        formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.cabRetrievalService = cabRetrievalService;
    }

    @RequestMapping(value = "/retrieve-count/{date}/{ignoreCache}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CabTripResponseDTO>> getCount(@PathVariable String date, @PathVariable Boolean ignoreCache, @RequestBody Set<String> medallions) throws JsonProcessingException {
        try {
            List<CabTripResponseDTO> cabTripResponseDTOS = cabRetrievalService.retrieveCabTrip(medallions, formatter.parse(date), ignoreCache);
            return new ResponseEntity<>(cabTripResponseDTOS, HttpStatus.OK);
        } catch (ParseException e) {
            throw new InvalidDateFormatException();
        }
    }

    @RequestMapping(value = "/clear-cache", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> clearCache() throws JsonProcessingException {
        return new ResponseEntity<>(cabRetrievalService.clearCache(), HttpStatus.OK);
    }
}
