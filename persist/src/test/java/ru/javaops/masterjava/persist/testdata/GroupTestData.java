package ru.javaops.masterjava.persist.testdata;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.GroupDao;
import ru.javaops.masterjava.persist.model.Group;

import java.util.List;

/**
 * Created by apyreev on 31-Mar-17.
 */
public class GroupTestData {
    public static Group TOPJAVA10;
    public static Group TOPJAVA09;
    public static Group TOPJAVA08;
    public static Group MASTERJAVA2;
    public static Group MASTERJAVA1;
    public static List<Group> GROUPS;

    public static void init() {
        TOPJAVA10 = new Group("topjava10", 1);
        TOPJAVA09 = new Group("topjava09", 1);
        TOPJAVA08 = new Group("topjava08", 1);
        MASTERJAVA2 = new Group("masterjava2", 2);
        MASTERJAVA1 = new Group("masterjava1", 2);
        GROUPS = ImmutableList.of(MASTERJAVA1, MASTERJAVA2, TOPJAVA08, TOPJAVA09, TOPJAVA10);
    }

    public static void setUp() {
        GroupDao dao = DBIProvider.getDao(GroupDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> GROUPS.forEach(dao::insert));
    }
}
