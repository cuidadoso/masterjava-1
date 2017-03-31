package ru.javaops.masterjava.persist.dao;

import one.util.streamex.IntStreamEx;
import org.skife.jdbi.v2.sqlobject.Transaction;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.model.BaseEntity;

import java.util.List;

/**
 * gkislin
 * 27.10.2016
 * <p>
 * <p>
 */
public abstract class AbstractDao<T extends BaseEntity> {

    abstract void clean();

    abstract int insertGeneratedId(T entity);

    abstract void insertWithId(T entity);

    abstract List<T> getWithLimit(int limit);

    abstract int[] insertBatch(List<T> projects, int chunkSize);

    abstract int getNextVal();

    abstract int getSeqAndSkip(int step);

    public List<String> insertAndGetAlreadyPresent(List<T> entity) {
        int[] result = insertBatch(entity, entity.size());
        return IntStreamEx.range(0, entity.size())
                .filter(i -> result[i] == 0)
                .mapToObj(index -> entity.get(index).getIndex())
                .toList();
    }

    public T insert(T entity) {
        if (entity.isNew()) {
            int id = insertGeneratedId(entity);
            entity.setId(id);
        } else {
            insertWithId(entity);
        }
        return entity;
    }

    @Transaction
    public int getSeqAndSkip(int step, String seq) {
        int id = getNextVal();
        String sql = "ALTER SEQUENCE " + seq + " RESTART WITH " + (id + step);
        DBIProvider.getDBI().useHandle(h -> h.execute(sql));
        return id;
    }
}
