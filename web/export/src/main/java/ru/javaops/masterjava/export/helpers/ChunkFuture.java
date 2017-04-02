package ru.javaops.masterjava.export.helpers;

import lombok.Getter;
import ru.javaops.masterjava.persist.model.BaseEntity;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by Alejandro on 02.04.2017.
 */
public class ChunkFuture<T extends BaseEntity> {
    @Getter
    private String indexRange;
    @Getter
    private Future<List<String>> future;

    public ChunkFuture(List<T> chunk, Future<List<String>> future)
    {
        this.future = future;
        this.indexRange = chunk.get(0).getIndex();
        if (chunk.size() > 1) {
            this.indexRange += '-' + chunk.get(chunk.size() - 1).getIndex();
        }
    }
}
