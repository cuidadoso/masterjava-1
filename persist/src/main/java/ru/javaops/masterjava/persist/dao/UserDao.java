package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.User;

import java.util.List;

/**
 * gkislin
 * 27.10.2016
 * <p>
 * <p>
 */
@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class UserDao extends AbstractDao<User> {

    //   http://stackoverflow.com/questions/13223820/postgresql-delete-all-content
    @SqlUpdate("TRUNCATE users")
    @Override
    public abstract void clean();

    @SqlUpdate("INSERT INTO users (full_name, email, flag) VALUES (:fullName, :email, CAST(:flag AS USER_FLAG)) ")
    @GetGeneratedKeys
    @Override
    abstract int insertGeneratedId(@BindBean User user);

    @SqlUpdate("INSERT INTO users (id, full_name, email, flag) VALUES (:id, :fullName, :email, CAST(:flag AS USER_FLAG)) ")
    @Override
    abstract void insertWithId(@BindBean User user);

    @SqlQuery("SELECT * FROM users ORDER BY full_name, email LIMIT :it")
    @Override
    public abstract List<User> getWithLimit(@Bind int limit);

    //    https://habrahabr.ru/post/264281/
    @SqlBatch("INSERT INTO users (id, full_name, email, flag) VALUES (:id, :fullName, :email, CAST(:flag AS USER_FLAG))" +
            "ON CONFLICT DO NOTHING")
//            "ON CONFLICT (email) DO UPDATE SET full_name=:fullName, flag=CAST(:flag AS USER_FLAG)")
    @Override
    public abstract int[] insertBatch(@BindBean List<User> users, @BatchChunkSize int chunkSize);

    @SqlQuery("SELECT nextval('user_seq')")
    @Override
    abstract int getNextVal();

    public int getSeqAndSkip(int step) {
        return getSeqAndSkip(step, "user_seq");
    }
}
