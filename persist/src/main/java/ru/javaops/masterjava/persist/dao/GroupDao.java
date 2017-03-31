package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.Group;

import java.util.List;

/**
 * Created by apyreev on 31-Mar-17.
 */
@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class GroupDao extends AbstractDao<Group> {
    @SqlUpdate("TRUNCATE groups")
    @Override
    public abstract void clean();

    @SqlUpdate("INSERT INTO groups (name, project_id) VALUES (:name, :projectId)")
    @GetGeneratedKeys
    @Override
    abstract int insertGeneratedId(@BindBean Group entity);

    @SqlUpdate("INSERT INTO groups (id, name, project_id) VALUES (:id, :name, :projectId)")
    @Override
    abstract void insertWithId(@BindBean Group entity);

    @SqlQuery("SELECT * FROM groups ORDER BY name LIMIT :it")
    @Override
    public abstract List<Group> getWithLimit(@Bind int limit);

    //    https://habrahabr.ru/post/264281/
    @SqlBatch("INSERT INTO groups (id, name, project_id) VALUES (:id, :name, :projectId) ON CONFLICT DO NOTHING")
    @Override
    public abstract int[] insertBatch(@BindBean List<Group> projects, @BatchChunkSize int chunkSize);

    @SqlQuery("SELECT nextval('group_seq')")
    @Override
    abstract int getNextVal();


    @Override
    public int getSeqAndSkip(int step) {
        return getSeqAndSkip(step, "group_seq");
    }
}
