package ru.javaops.masterjava.export.helpers;

import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import ru.javaops.masterjava.persist.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro on 02.04.2017.
 */
@Slf4j
public class Helper {

    private static Helper instance;

    private static class LazyHolder {
        private static final Helper INSTANCE = new Helper();
    }
    public static Helper getInstance() {
        return LazyHolder.INSTANCE;
    }

    public List<FailedIndex> getFailedIndex(List<ChunkFuture<BaseEntity>> futures) {
        List<FailedIndex> failed = new ArrayList<>();
        futures.forEach(cf -> {
            try {
                failed.addAll(StreamEx.of(cf.getFuture().get())
                        .map(index -> new FailedIndex(index, "already present"))
                        .toList());
                log.info(cf.getIndexRange() + " successfully executed");
            } catch (Exception e) {
                log.error(cf.getIndexRange() + " failed", e);
                failed.add(new FailedIndex(cf.getIndexRange(), e.toString()));
            }
        });
        return failed;
    }
}
