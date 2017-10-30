package com.datarepublic.simplecab.services;

import com.datarepublic.simplecab.domain_model.CabTripResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by ranjeethpt on 30/10/17.
 *
 * @author ranjeethpt
 */
public class SimpleCabServiceImpl implements SimpleCabService {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final DateFormat formatter;

    public SimpleCabServiceImpl(RestTemplate restTemplate, String baseUrl) {
        checkNotNull(restTemplate);
        checkArgument(isNotBlank(baseUrl));

        formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean deleteCache() {
        return "SUCCESS".equalsIgnoreCase(restTemplate.postForObject(baseUrl + "/clear-cache", null, String.class));
    }

    @Override
    public CabTripResponse[] getMedallionsSummary(Date pickupDate, String... medallions) {
        return getMedallionsSummary(pickupDate, false, medallions);
    }

    @Override
    public CabTripResponse[] getMedallionsSummary(Date pickupDate, boolean ignoreCache, String... medallions) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String[]> entity = new HttpEntity<>(medallions, headers);
        return restTemplate.postForObject(baseUrl + "/retrieve-count/" + formatter.format(pickupDate) + "/" + ignoreCache, entity, CabTripResponse[].class);
    }

}
