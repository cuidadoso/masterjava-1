package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.City;

import java.util.List;

/**
 * Created by apyreev on 31-Mar-17.
 */
@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class CityDao extends AbstractDao<City> {

    @SqlUpdate("TRUNCATE cities")
    @Override
    public abstract void clean();

    @SqlUpdate("INSERT INTO cities (id_name, name) VALUES (:idName, :name)")
    @GetGeneratedKeys
    @Override
    abstract int insertGeneratedId(@BindBean City entity);

    @SqlUpdate("INSERT INTO cities (id, id_name, name) VALUES (:id, :idName, :name)")
    @Override
    abstract void insertWithId(@BindBean City entity);

    @SqlQuery("SELECT * FROM cities ORDER BY name LIMIT :it")
    @Override
    public abstract List<City> getWithLimit(@Bind int limit);

    //    https://habrahabr.ru/post/264281/
    @SqlBatch("INSERT INTO cities (id, id_name, name) VALUES (:id, :idName, :name) ON CONFLICT DO NOTHING")
    @Override
    public abstract int[] insertBatch(@BindBean List<City> projects, @BatchChunkSize int chunkSize);

    @SqlQuery("SELECT nextval('city_seq')")
    @Override
    abstract int getNextVal();

    @Override
    public int getSeqAndSkip(int step) {
        return getSeqAndSkip(step, "city_seq");
    }
}
