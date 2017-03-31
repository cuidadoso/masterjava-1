package ru.javaops.masterjava.persist.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javaops.masterjava.persist.model.City;
import ru.javaops.masterjava.persist.testdata.CityTestData;

import java.util.List;

import static ru.javaops.masterjava.persist.testdata.CityTestData.CITIES;

/**
 * Created by apyreev on 31-Mar-17.
 */
public class CityDaoTest extends AbstractDaoTest<CityDao> {

    public CityDaoTest() {
        super(CityDao.class);
    }

    @BeforeClass
    public static void init() throws Exception {
        CityTestData.init();
    }

    @Before
    public void setUp() throws Exception {
        CityTestData.setUp();
    }

    @Test
    public void getWithLimit() {
        List<City> cities = dao.getWithLimit(5);
        Assert.assertEquals(CITIES, cities);
    }

    @Test
    public void insertBatch() throws Exception {
        dao.clean();
        dao.insertBatch(CITIES, 3);
        Assert.assertEquals(4, dao.getWithLimit(100).size());
    }

    @Test
    public void getSeqAndSkip() throws Exception {
        int seq1 = dao.getSeqAndSkip(5);
        int seq2 = dao.getSeqAndSkip(1);
        Assert.assertEquals(5, seq2 - seq1);
    }
}
