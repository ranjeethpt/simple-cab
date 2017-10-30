package com.datarepublic.simplecab.web.controller;

import com.datarepublic.simplecab.configuration.TestContextConfiguration;
import com.datarepublic.simplecab.configuration.WebConfiguration;
import com.datarepublic.simplecab.domain_model.CabTripResponseDTO;
import com.datarepublic.simplecab.service.CabRetrievalService;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newLinkedHashSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {WebConfiguration.class, TestContextConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(secure = false)
public class CabControllerTest {

    private String port;

    @Autowired
    Environment environment;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CabRetrievalService cabRetrievalService;

    @Before
    public void setUp() {
        port = environment.getProperty("local.server.port");
    }

    /**
     * It should delegate the call to {@link CabRetrievalService#retrieveCabTrip(Set, Date, Boolean)}
     */
    @Test
    public void testGetCountWithCache() throws Exception {
        Set<String> medallions = newLinkedHashSet();

        List<CabTripResponseDTO> cabTripResponseDTOS = newArrayList(CabTripResponseDTO.builder().count(1).medallion("Groot").build(),
                CabTripResponseDTO.builder().count(2).medallion("Rocket Racoon").build());

        when(cabRetrievalService.retrieveCabTrip(medallions,
                new GregorianCalendar(2000, 11, 31, 0, 0, 0).getTime(), true))
                .thenReturn(cabTripResponseDTOS);

        ResponseEntity<String> jsonResponse = testRestTemplate.postForEntity("http://localhost:" + port + "/services/retrieve-count/2000-12-31/true", medallions, String.class);
        assertThat(jsonResponse).isNotNull();
        assertThat(jsonResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jsonResponse.getHeaders()).isNotNull().isNotEmpty().containsKey("Content-Type");
        assertThat(jsonResponse.getHeaders().get("Content-Type")).hasSize(1).contains("application/json;charset=UTF-8");
        assertThat(jsonResponse.getBody()).isNotNull().isNotEqualTo("");
        ReadContext ctx = JsonPath.parse(jsonResponse.getBody());
        assertThat((String) ctx.read("$.[0].medallion")).isEqualTo("Groot");
        assertThat((Integer) ctx.read("$.[0].count")).isEqualTo(1);
        assertThat((String) ctx.read("$.[1].medallion")).isEqualTo("Rocket Racoon");
        assertThat((Integer) ctx.read("$.[1].count")).isEqualTo(2);
    }

    @Test
    public void testCleanCache() {
        when(cabRetrievalService.clearCache()).thenReturn("Groot");

        ResponseEntity<String> jsonResponse = testRestTemplate.postForEntity("http://localhost:" + port + "/services/clear-cache", null, String.class);
        assertThat(jsonResponse).isNotNull();
        assertThat(jsonResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jsonResponse.getHeaders()).isNotNull().isNotEmpty().containsKey("Content-Type");
        assertThat(jsonResponse.getHeaders().get("Content-Type")).hasSize(1).contains("text/html;charset=UTF-8");
        assertThat(jsonResponse.getBody()).isNotNull().isEqualTo("Groot");
    }

}