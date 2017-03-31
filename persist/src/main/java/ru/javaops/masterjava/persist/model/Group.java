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
public class Group extends BaseEntity {
    private @NonNull String name;
    @Column("project_id")
    private @NonNull Integer projectId;

    public Group(Integer id, String name, Integer projectId) {
        this(name, projectId);
        this.id = id;
    }

    @Override
    public String getIndex() {
        return getName();
    }
}
