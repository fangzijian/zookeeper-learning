package com.fzj.zookeeper;

import static com.fzj.zookeeper.Constants.*;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class CuratorDemo {
    public static void main(String[] args) throws Exception {

        CuratorFramework curatorFramework = CuratorFrameworkFactory.
                builder().connectString(IP).
                sessionTimeoutMs(4000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).
                namespace("").build();
        curatorFramework.start();
        Stat stat = new Stat();
        //查询节点数据
        byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath(NODE);
        System.out.println(new String(bytes));
        //查询子节点列表
        List<String> strings = curatorFramework.getChildren().storingStatIn(stat).forPath(ROOT);
        System.out.println(strings);
        curatorFramework.close();
    }
}
