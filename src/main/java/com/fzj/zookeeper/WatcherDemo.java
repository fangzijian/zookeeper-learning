package com.fzj.zookeeper;

import static com.fzj.zookeeper.Constants.*;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class WatcherDemo implements Watcher {
    static ZooKeeper zooKeeper;

    static {
        try {
            zooKeeper = new ZooKeeper(IP, 4000, new WatcherDemo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        // 节点发生变化时候会收到回调
        System.out.println("eventType:" + event.getType());
        /*if (event.getType() == Event.EventType.NodeDataChanged) {
        }*/
        try {
            zooKeeper.exists(event.getPath(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        /*if (zooKeeper.exists(WATCHER, false) == null) {
            zooKeeper.create(WATCHER, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }*/
        Thread.sleep(1000);
        System.out.println("程序启动-----------");
        //true表示使用zookeeper实例中配置的watcher
        Stat stat = zooKeeper.exists(WATCHER, true);
        // 让程序保持运行。
        System.in.read();
    }
}
