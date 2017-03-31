package ru.javaops.masterjava.persist.model;

import lombok.*;

/**
 * Created by apyreev on 31-Mar-17.
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Project extends BaseEntity {
    private @NonNull String name;

    public Project(Integer id, String name) {
        this(name);
        this.id = id;
    }

    @Override
    public String getIndex() {
        return getName();
    }
}
