package com.zhangjie.zookeeper.study;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class TestZookeeper {

    private static final String CONNECT = "node2:2181,node3:2181,node4:2181";
    private static final int SESSIONTIMEOUT = 2000;
    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {

        zkClient = new ZooKeeper(CONNECT, SESSIONTIMEOUT, watcher -> {
//            System.out.println(watcher.getWrapper().getType()+"--->"+watcher.getWrapper().getPath());
//            try {
//                zkClient.getChildren("/",true);
//            } catch (KeeperException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        });
    }

    /**
     * 1.测试创建节点
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void createNode() throws KeeperException, InterruptedException {
        String path = zkClient.create("/zhangjie", "zhangjiezuishuai".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
    }

    /**
     * 2.测试获取子节点并监听数据的变化
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void getChildren() throws KeeperException, InterruptedException {
        zkClient.getChildren("/",watcher->{
            try {
                List<String> children = zkClient.getChildren("/",true);
                for (String child : children) {
                    System.out.println(child);
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 3. 判断ZNode是否存在
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void exists() throws KeeperException, InterruptedException {
        Stat stat = zkClient.exists("/sanguo", false);
        System.out.println(stat == null ?"not exists": "exists");
    }
}
