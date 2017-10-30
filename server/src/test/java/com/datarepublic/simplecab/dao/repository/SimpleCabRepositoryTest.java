package com.datarepublic.simplecab.dao.repository;

import com.datarepublic.simplecab.dao.entity.CabTripData;
import com.datarepublic.simplecab.dao.entity.CabTripDataCompositeKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.GregorianCalendar;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SimpleCabRepositoryTest {

    @Autowired
    private SimpleCabRepository simpleCabRepository;

    private Date date1 = new GregorianCalendar(2015, 12, 12, 12, 12, 12).getTime();
    private Date date2 = new GregorianCalendar(2015, 12, 13, 12, 12, 12).getTime();
    private Date date3 = new GregorianCalendar(2015, 12, 14, 12, 12, 12).getTime();
    private Date date4 = new GregorianCalendar(2015, 12, 12, 1, 2, 3).getTime();
    private Date date5 = new GregorianCalendar(2015, 12, 12, 4, 5, 6).getTime();


    /**
     * This should get the count based on Medallion And PickupDate
     */
    @Test
    public void testGetCountByMedallionAndPickupDatetime() {
        CabTripDataCompositeKey cabTripDataCompositeKey1 = new CabTripDataCompositeKey("Groot!!", "lic 1", date1, date2);
        CabTripData cabTripData1 = new CabTripData(cabTripDataCompositeKey1, "Vendor 1", 1, "FLAG1", 3, 12345, 123.45);

        CabTripDataCompositeKey cabTripDataCompositeKey2 = new CabTripDataCompositeKey("Groot!!", "lic 1", date4, date3);
        CabTripData cabTripData2 = new CabTripData(cabTripDataCompositeKey2, "Vendor 1", 1, "FLAG1", 3, 12345, 123.45);


        CabTripDataCompositeKey cabTripDataCompositeKey3 = new CabTripDataCompositeKey("Groot!!", "lic 1", date2, date3);
        CabTripData cabTripData3 = new CabTripData(cabTripDataCompositeKey3, "Vendor 1", 1, "FLAG1", 3, 12345, 123.45);


        CabTripDataCompositeKey cabTripDataCompositeKey4 = new CabTripDataCompositeKey("Groot!!", "lic 2", date5, date2);
        CabTripData cabTripData4 = new CabTripData(cabTripDataCompositeKey4, "Vendor 1", 1, "FLAG1", 3, 12345, 123.45);

        simpleCabRepository.saveAll(newArrayList(cabTripData1, cabTripData2, cabTripData3, cabTripData4));

        assertThat(simpleCabRepository.countById_MedallionAndId_PickupDatetime("Groot!!",
                new GregorianCalendar(2015, 12, 12, 0, 0, 0).getTime())).isEqualTo(3);
    }

    /**
     * It should return 0 when no data is found
     */
    @Test
    public void testNoDataFound() {
        assertThat(simpleCabRepository.countById_MedallionAndId_PickupDatetime("Groot!!",
                new GregorianCalendar(2016, 12, 12, 0, 0, 0).getTime())).isEqualTo(0);
    }

}