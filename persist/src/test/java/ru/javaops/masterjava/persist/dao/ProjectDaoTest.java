package ru.javaops.masterjava.persist.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javaops.masterjava.persist.model.Project;
import ru.javaops.masterjava.persist.testdata.ProjectTestData;

import java.util.List;

import static ru.javaops.masterjava.persist.testdata.ProjectTestData.PROJECTS;
/**
 * Created by apyreev on 31-Mar-17.
 */
public class ProjectDaoTest extends AbstractDaoTest<ProjectDao> {

    public ProjectDaoTest() {
        super(ProjectDao.class);
    }

    @BeforeClass
    public static void init() throws Exception {
        ProjectTestData.init();
    }

    @Before
    public void setUp() throws Exception {
        ProjectTestData.setUp();
    }

    @Test
    public void getWithLimit() {
        List<Project> projects = dao.getWithLimit(5);
        Assert.assertEquals(PROJECTS, projects);
    }

    @Test
    public void insertBatch() throws Exception {
        dao.clean();
        dao.insertBatch(PROJECTS, 3);
        Assert.assertEquals(2, dao.getWithLimit(100).size());
    }

    @Test
    public void getSeqAndSkip() throws Exception {
        int seq1 = dao.getSeqAndSkip(5);
        int seq2 = dao.getSeqAndSkip(1);
        Assert.assertEquals(5, seq2 - seq1);
    }
}
