package ru.javaops.masterjava.export;

import lombok.extern.slf4j.Slf4j;
import ru.javaops.masterjava.export.helpers.ChunkFuture;
import ru.javaops.masterjava.export.helpers.FailedIndex;
import ru.javaops.masterjava.export.helpers.Helper;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.GroupDao;
import ru.javaops.masterjava.persist.model.BaseEntity;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.GroupType;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Alejandro on 02.04.2017.
 */
@Slf4j
public class GroppExport {
    private static final int NUMBER_THREADS = 4;
    private final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_THREADS);
    private GroupDao dao = DBIProvider.getDao(GroupDao.class);

    public List<FailedIndex> process(final InputStream is, int chunkSize) throws XMLStreamException
    {
        log.info("Start proseccing with chunkSize=" + chunkSize);

        Helper helper = Helper.getInstance();
        return new Callable<List<FailedIndex>>() {
            @Override
            public List<FailedIndex> call() throws XMLStreamException {
                List<ChunkFuture<BaseEntity>> futures = new ArrayList<>();

                int id = dao.getSeqAndSkip(chunkSize);
                List<Group> chunk = new ArrayList<>(chunkSize);
                final StaxStreamProcessor processor = new StaxStreamProcessor(is);

                while (processor.doUntil(XMLEvent.START_ELEMENT, "Group")) {
                    final String name = processor.getAttribute("name");
                    final GroupType type = GroupType.valueOf(processor.getAttribute("type"));
                    // TODO projectId bind to projects fom db.
                    final Group group = new Group(id++, name, type, 1);
                    chunk.add(group);
                    if (chunk.size() == chunkSize) {
                        futures.add(submit(chunk));
                        chunk = new ArrayList<>(chunkSize);
                        id = dao.getSeqAndSkip(chunkSize);
                    }
                }
                if (!chunk.isEmpty()) {
                    futures.add(submit(chunk));
                }
                return helper.getFailedIndex(futures);
            }

            private ChunkFuture submit(List<Group> chunk) {
                ChunkFuture<Group> chunkFuture = new ChunkFuture<>(chunk,
                        executorService.submit(() -> dao.insertAndGetAlreadyPresent(chunk))
                );
                log.info("Submit " + chunkFuture.getIndexRange());
                return chunkFuture;
            }
        }.call();
    }
}
