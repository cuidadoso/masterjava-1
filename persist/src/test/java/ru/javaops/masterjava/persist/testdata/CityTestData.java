package ru.javaops.masterjava.persist.testdata;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.CityDao;
import ru.javaops.masterjava.persist.model.City;

import java.util.List;

/**
 * Created by apyreev on 31-Mar-17.
 */
public class CityTestData {
    public static City MSK;
    public static City SPB;
    public static City NSK;
    public static City GDSK;
    public static List<City> CITIES;

    public static void init() {
        MSK = new City("MSK", "Moscow");
        SPB = new City("SPB", "St-Peterburg");
        NSK = new City("NSK", "Novosibirsk");
        GDSK = new City("GDSK", "Gdansk");
        CITIES = ImmutableList.of(GDSK, MSK, NSK, SPB);
    }

    public static void setUp() {
        CityDao dao = DBIProvider.getDao(CityDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> CITIES.forEach(dao::insert));
    }

}
