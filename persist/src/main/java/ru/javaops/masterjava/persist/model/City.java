package ru.javaops.masterjava.persist.model;

import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.*;

/**
 * Created by apyreev on 31-Mar-17.
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class City extends BaseEntity {
    @Column("id_name")
    private @NonNull String idName;
    private @NonNull String name;

    public City(Integer id, String idName, String name) {
        this(idName, name);
        this.id = id;
    }

    @Override
    public String getIndex() {
        return getIdName();
    }
}
