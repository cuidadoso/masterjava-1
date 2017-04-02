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
    private @NonNull String description;

    public Project(Integer id, String name, String description) {
        this(name, description);
        this.id = id;
    }

    @Override
    public String getIndex() {
        return getName();
    }
}
