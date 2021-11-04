package com.fzj.zookeeper;

import static com.fzj.zookeeper.Constants.IP;
import static com.fzj.zookeeper.Constants.WATCHER;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

public class ConnectDemo {
    public static void main(String[] args) {
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper(IP, 4000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (Event.KeeperState.SyncConnected == event.getState()) {
                        //如果收到了服务端的响应事件，连接成功
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
            //CONNECTED
            System.out.println(zooKeeper.getState());

            //创建
            if (zooKeeper.exists(WATCHER, false) == null) {
                zooKeeper.create(WATCHER, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            zooKeeper.delete(WATCHER, 0);

        } catch (Throwable ignored) {

        }
    }
}
