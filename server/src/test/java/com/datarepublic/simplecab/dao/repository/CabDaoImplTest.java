package com.datarepublic.simplecab.dao.repository;

import com.datarepublic.simplecab.configuration.CacheConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CabDaoImplTest.TestConfiguration.class, CacheConfiguration.class})
public class CabDaoImplTest {

    @Autowired
    private SimpleCabRepository simpleCabRepository;

    @Autowired
    private CabDao cabDao;

    @Configuration
    static class TestConfiguration {
        @Bean
        public SimpleCabRepository simpleCabRepository() {
            return mock(SimpleCabRepository.class);
        }

        @Bean
        public CabDao cabDao(SimpleCabRepository simpleCabRepository) {
            return new CabDaoImpl(simpleCabRepository);
        }
    }

    @Before
    public void setup() {
        reset(simpleCabRepository);
    }

    /**
     * It should delegate the call to {@link SimpleCabRepository#countById_MedallionAndId_PickupDatetime(String, Date)}
     * and only call once when called with same argument
     */
    @Test
    public void testGetCountByMedallionAndPickupDatetime() {
        Date date = new GregorianCalendar(2015, 12, 12, 0, 0, 0).getTime();
        cabDao.getCountByMedallionAndPickupDatetime("Groot", date);
        cabDao.getCountByMedallionAndPickupDatetime("Groot", date);
        cabDao.getCountByMedallionAndPickupDatetime("Groot", date);
        verify(simpleCabRepository, times(1)).countById_MedallionAndId_PickupDatetime("Groot", date);
    }

    /**
     * It should delegate the call to {@link SimpleCabRepository#countById_MedallionAndId_PickupDatetime(String, Date)}
     * multiple times when cache is cleared.
     */
    @Test
    public void testCleanCache() {
        Date date = new GregorianCalendar(2015, 12, 12, 0, 0, 0).getTime();
        cabDao.getCountByMedallionAndPickupDatetime("Rocket Racoon", date);

        cabDao.cleanCache("Rocket Racoon", date);

        cabDao.getCountByMedallionAndPickupDatetime("Rocket Racoon", date);
        verify(simpleCabRepository, times(2)).countById_MedallionAndId_PickupDatetime("Rocket Racoon", date);
    }

    /**
     * It should clear all contents of the cache.
     */
    @Test
    public void testClearCache() {
        Date date = new GregorianCalendar(2015, 12, 12, 0, 0, 0).getTime();
        cabDao.getCountByMedallionAndPickupDatetime("Thanos", date);
        cabDao.getCountByMedallionAndPickupDatetime("Ronana", date);
        cabDao.getCountByMedallionAndPickupDatetime("Ego", date);

        assertThat(cabDao.clearCache()).isEqualTo("SUCCESS");

        cabDao.getCountByMedallionAndPickupDatetime("Thanos", date);
        cabDao.getCountByMedallionAndPickupDatetime("Ronana", date);
        cabDao.getCountByMedallionAndPickupDatetime("Ego", date);
        verify(simpleCabRepository, times(2)).countById_MedallionAndId_PickupDatetime("Thanos", date);
        verify(simpleCabRepository, times(2)).countById_MedallionAndId_PickupDatetime("Ronana", date);
        verify(simpleCabRepository, times(2)).countById_MedallionAndId_PickupDatetime("Ego", date);

    }
}