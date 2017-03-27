package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.User;
import ru.javaops.masterjava.persist.model.UserFlag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * gkislin
 * 27.10.2016
 * <p>
 * <p>
 */
@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class UserDao implements AbstractDao {

    public User insert(User user) {
        if (user.isNew()) {
            int id = insertGeneratedId(user);
            user.setId(id);
        } else {
            insertWitId(user);
        }
        return user;
    }

    public int insertUsers(List<User> users) {
        List<String> fullNames = new ArrayList<>();
        List<String> emails = new ArrayList<>();
        List<UserFlag> flags = new ArrayList<>();
        users.forEach(user -> {
            fullNames.add(user.getFullName());
            emails.add(user.getEmail());
            flags.add(user.getFlag());
        });
        insertBatch(fullNames, emails, flags);
        return users.size();
    }

    @SqlUpdate("INSERT INTO users (full_name, email, flag) VALUES (:fullName, :email, CAST(:flag AS user_flag))")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean User user);

    @SqlUpdate("INSERT INTO users (id, full_name, email, flag) VALUES (:id, :fullName, :email, CAST(:flag AS user_flag)) ")
    abstract void insertWitId(@BindBean User user);

    @SqlBatch("INSERT INTO users (full_name, email, flag) VALUES (:full_name, :email, CAST(:flag AS user_flag))")
    @GetGeneratedKeys
    @BatchChunkSize(1000)
    abstract void insertBatch(@Bind("full_name") List<String> fullNames,
                              @Bind("email") List<String> emails,
                              @Bind("flag") List<UserFlag> flags);

    @SqlQuery("SELECT * FROM users ORDER BY full_name, email LIMIT :it")
    public abstract List<User> getWithLimit(@Bind int limit);

    //   http://stackoverflow.com/questions/13223820/postgresql-delete-all-content
    @SqlUpdate("TRUNCATE users")
    @Override
    public abstract void clean();
}
