package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.Project;

import java.util.List;

/**
 * Created by apyreev on 31-Mar-17.
 */
@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class ProjectDao extends AbstractDao<Project> {

    @SqlUpdate("TRUNCATE projects")
    @Override
    public abstract void clean();

    @SqlUpdate("INSERT INTO projects (name, description) VALUES (:name, :description)")
    @GetGeneratedKeys
    @Override
    abstract int insertGeneratedId(@BindBean Project project);

    @SqlUpdate("INSERT INTO projects (id, name, description) VALUES (:id, :name, :description)")
    @Override
    abstract void insertWithId(@BindBean Project project);

    @SqlQuery("SELECT * FROM projects ORDER BY name LIMIT :it")
    @Override
    public abstract List<Project> getWithLimit(@Bind int limit);

    //    https://habrahabr.ru/post/264281/
    @SqlBatch("INSERT INTO projects (id, name, description) VALUES (:id, :name, :description) ON CONFLICT DO NOTHING")
    @Override
    public abstract int[] insertBatch(@BindBean List<Project> projects, @BatchChunkSize int chunkSize);

    @SqlQuery("SELECT nextval('project_seq')")
    @Override
    abstract int getNextVal();

    @Override
    public int getSeqAndSkip(int step) {
        return getSeqAndSkip(step, "project_seq");
    }
}
