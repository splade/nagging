package info.alex.learn.zookeeper.learnzookeeper.zk.sample.app;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.zookeeper.Watcher.Event.KeeperState;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 30000,
                (WatchedEvent event) -> {
            KeeperState state = event.getState();
            if (state == KeeperState.SyncConnected) {
                LOGGER.info("Connected. {}", event);
            } else {
                LOGGER.error("Connect zookeeper error: {}", event);
            }

            LOGGER.debug("WatchedEvent: {}", event);

            latch.countDown();
        });

        String path = "/learn-zookeeper-test";
        zooKeeper.exists(path, false, (int rc, String znodePath, Object o, Stat stat) -> {
            LOGGER.info("{}, {}, {}, {}", rc, znodePath, o, stat);


            if (stat == null) {
                try {
                    zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                } catch (KeeperException e) {
                    LOGGER.warn("Create ZNode exception: {}", e.getMessage());
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    LOGGER.warn("Create ZNode exception: {}", e.getMessage());
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            }

        }, "this is a context string");

        latch.await();
        // Exception in thread "main" org.apache.zookeeper.KeeperException$NoAuthException: KeeperErrorCode = NoAuth for /learn-zookeeper-test
        Stat stat = zooKeeper.setData(path, "Hello ZooKeeper!".getBytes(), -1);

        LOGGER.info("{}", stat);
    }
}
