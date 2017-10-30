package com.datarepublic.simplecab.service;

import com.datarepublic.simplecab.dao.repository.CabDao;
import com.datarepublic.simplecab.domain_model.CabTripResponseDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newLinkedHashSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@RunWith(MockitoJUnitRunner.class)
public class CabRetrievalServiceTest {

    @Mock
    private CabDao cabDao;

    private CabRetrievalService cabRetrievalService;

    @Before
    public void setUp() {
        cabRetrievalService = new CabRetrievalServiceImpl(cabDao);
    }

    /**
     * It should call {@link CabDao#getCountByMedallionAndPickupDatetime(String, Date)} for 3 times and return the result accordingly
     */
    @Test
    public void testRetrieveCabTrip() {
        Date date = new GregorianCalendar(2015, 12, 12, 0, 0, 0).getTime();
        when(cabDao.getCountByMedallionAndPickupDatetime("Groot", date)).thenReturn(8);
        when(cabDao.getCountByMedallionAndPickupDatetime("Rocket Racoon", date)).thenReturn(1);
        when(cabDao.getCountByMedallionAndPickupDatetime("Star lord", date)).thenReturn(4);

        Set<String> medallions = newLinkedHashSet();
        medallions.add("Groot");
        medallions.add("Rocket Racoon");
        medallions.add("Star lord");

        List<CabTripResponseDTO> result = cabRetrievalService.retrieveCabTrip(medallions, date, false);

        verify(cabDao, never()).cleanCache("Groot", date);
        verify(cabDao, never()).cleanCache("Rocket Racoon", date);
        verify(cabDao, never()).cleanCache("Star lord", date);

        assertThat(result).isNotNull().isNotEmpty().hasSize(3);
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getMedallion()).isNotNull().isEqualTo("Groot");
        assertThat(result.get(0).getCount()).isNotNull().isEqualTo(8);

        assertThat(result.get(1)).isNotNull();
        assertThat(result.get(1).getMedallion()).isNotNull().isEqualTo("Rocket Racoon");
        assertThat(result.get(1).getCount()).isNotNull().isEqualTo(1);

        assertThat(result.get(2)).isNotNull();
        assertThat(result.get(2).getMedallion()).isNotNull().isEqualTo("Star lord");
        assertThat(result.get(2).getCount()).isNotNull().isEqualTo(4);
    }


    /**
     * It should call {@link CabDao#getCountByMedallionAndPickupDatetime(String, Date)} for 3 times and return the result accordingly
     * and also call the clean cache
     */
    @Test
    public void testRetrieveCabTripWithCache() {
        Date date = new GregorianCalendar(2015, 12, 12, 0, 0, 0).getTime();
        when(cabDao.getCountByMedallionAndPickupDatetime("Groot", date)).thenReturn(8);
        when(cabDao.getCountByMedallionAndPickupDatetime("Rocket Racoon", date)).thenReturn(1);
        when(cabDao.getCountByMedallionAndPickupDatetime("Star lord", date)).thenReturn(4);

        Set<String> medallions = newLinkedHashSet();
        medallions.add("Groot");
        medallions.add("Rocket Racoon");
        medallions.add("Star lord");

        List<CabTripResponseDTO> result = cabRetrievalService.retrieveCabTrip(medallions, date, true);
        verify(cabDao).cleanCache("Groot", date);
        verify(cabDao).cleanCache("Rocket Racoon", date);
        verify(cabDao).cleanCache("Star lord", date);

        assertThat(result).isNotNull().isNotEmpty().hasSize(3);
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getMedallion()).isNotNull().isEqualTo("Groot");
        assertThat(result.get(0).getCount()).isNotNull().isEqualTo(8);

        assertThat(result.get(1)).isNotNull();
        assertThat(result.get(1).getMedallion()).isNotNull().isEqualTo("Rocket Racoon");
        assertThat(result.get(1).getCount()).isNotNull().isEqualTo(1);

        assertThat(result.get(2)).isNotNull();
        assertThat(result.get(2).getMedallion()).isNotNull().isEqualTo("Star lord");
        assertThat(result.get(2).getCount()).isNotNull().isEqualTo(4);
    }

    /**
     * It should delegate the call to {@link CabDao#clearCache()}
     */
    @Test
    public void testclearCache() {
        cabRetrievalService.clearCache();
        verify(cabDao).clearCache();
    }

}