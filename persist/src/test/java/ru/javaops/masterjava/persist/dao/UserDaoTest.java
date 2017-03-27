package ru.javaops.masterjava.persist.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.UserTestData;
import ru.javaops.masterjava.persist.model.User;

import java.util.Iterator;
import java.util.List;

import static ru.javaops.masterjava.persist.UserTestData.FIST5_USERS;
import static ru.javaops.masterjava.persist.UserTestData.USER3;

/**
 * gkislin
 * 27.10.2016
 */
public class UserDaoTest extends AbstractDaoTest<UserDao> {

    public UserDaoTest() {
        super(UserDao.class);
    }

    @BeforeClass
    public static void init() throws Exception {
        UserTestData.init();
    }

    @Before
    public void setUp() throws Exception {
        UserTestData.setUp();
    }

    @Test
    public void getWithLimit() {
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            FIST5_USERS.forEach(dao::insert);
            dao.insert(USER3);
        });

        List<User> users = dao.getWithLimit(5);
        Assert.assertEquals(FIST5_USERS, users);
    }

    @Test
    public void insertBatch() {
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            dao.insertUsers(FIST5_USERS);
        });
        List<User> users = dao.getWithLimit(10);

        Iterator<User> iter1 = FIST5_USERS.iterator();
        Iterator<User> iter2 = users.iterator();
        User user1;
        User user2;
        while (iter1.hasNext()) {
            user1 = iter1.next();
            user2 = iter2.next();
            user1.setId(user2.getId());
            Assert.assertEquals(user1, user2);
        }
    }
}