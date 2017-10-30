package com.datarepublic.simplecab.services;

import com.datarepublic.simplecab.domain_model.CabTripResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by ranjeethpt on 30/10/17.
 *
 * @author ranjeethpt
 */
public class SimpleCabServiceTest {

    private MockRestServiceServer mockServer;

    private SimpleCabService simpleCabService;

    private String baseUrl = "http://IamGroot.com";

    @Before
    public void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        simpleCabService = new SimpleCabServiceImpl(restTemplate, baseUrl);
    }

    /**
     * It should call the delete cache url
     */
    @Test
    public void testDeleteCache() {
        mockServer.expect(requestTo(baseUrl + "/clear-cache"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("SUCCESS", TEXT_HTML));
        assertThat(simpleCabService.deleteCache()).isTrue();
    }

    /**
     * It should call the delete cache url and return false as the response is not SUCCESS
     */
    @Test
    public void testDeleteCacheFailed() {
        mockServer.expect(requestTo(baseUrl + "/clear-cache"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("FROOT", TEXT_HTML));
        assertThat(simpleCabService.deleteCache()).isFalse();
    }

    /**
     * It should call the retrieve-count url.
     */
    @Test
    public void testGetMedallionsSummary() {
        mockServer.expect(requestTo(baseUrl + "/retrieve-count/2000-12-31/false"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0]", is("Groot")))
                .andExpect(jsonPath("$.[1]", is("Rocket Racoon")))
                .andRespond(withSuccess(new ClassPathResource("cab-data-success-response.json", SimpleCabServiceTest.class), APPLICATION_JSON));

        CabTripResponse[] responses = simpleCabService.getMedallionsSummary(new GregorianCalendar(2000, 11, 31, 0, 0, 0).getTime(), "Groot", "Rocket Racoon");
        assertThat(responses).isNotNull().isNotEmpty().hasSize(2);
        assertThat(responses[0]).isNotNull();
        assertThat(responses[0].getMedallion()).isEqualTo("Groot");
        assertThat(responses[0].getCount()).isEqualTo(10);
        assertThat(responses[1]).isNotNull();
        assertThat(responses[1].getMedallion()).isEqualTo("Rocket Racoon");
        assertThat(responses[1].getCount()).isEqualTo(5);
    }

    /**
     * It should call the retrieve-count url with cache.
     */
    @Test
    public void testGetMedallionsSummaryIgnoreCache() {
        mockServer.expect(requestTo(baseUrl + "/retrieve-count/2000-12-31/true"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0]", is("Groot")))
                .andExpect(jsonPath("$.[1]", is("Rocket Racoon")))
                .andRespond(withSuccess(new ClassPathResource("cab-data-success-response.json", SimpleCabServiceTest.class), APPLICATION_JSON));

        CabTripResponse[] responses = simpleCabService.getMedallionsSummary(new GregorianCalendar(2000, 11, 31, 0, 0, 0).getTime(), true, "Groot", "Rocket Racoon");
        assertThat(responses).isNotNull().isNotEmpty().hasSize(2);
        assertThat(responses[0]).isNotNull();
        assertThat(responses[0].getMedallion()).isEqualTo("Groot");
        assertThat(responses[0].getCount()).isEqualTo(10);
        assertThat(responses[1]).isNotNull();
        assertThat(responses[1].getMedallion()).isEqualTo("Rocket Racoon");
        assertThat(responses[1].getCount()).isEqualTo(5);
    }

}