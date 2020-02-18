package com.zhangjie.zookeeper.study;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * ZK的实际案例：服务器节点动态上下线
 * 服务器上下线往ZK集群的ZNODE中写数据（必须创建临时节点）
 */
public class DistributeServer {
    private ZooKeeper zkClient;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        // 创建ZKClient，并连接到ZKServer集群
        DistributeServer server = new DistributeServer();
        server.getConnect();
        // 注册节点
        server.regist(args[0]);
        // 业务逻辑
        server.bussiness();
    }

    private void bussiness() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 上线服务器注册信息
     * @param host
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void regist(String host) throws KeeperException, InterruptedException {
        String path = zkClient.create("/servers/server"+host,("node"+host).getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(("node"+host)+" is online!");
    }

    /**
     * 获取zk的客户端
     * @throws IOException
     */
    private void getConnect() throws IOException {
        String cluster = "node2:2181,node3:2181,node4:2181";
        int sessionTimeout = 2000;
        zkClient = new ZooKeeper(cluster, sessionTimeout, watcher -> {

        });
    }
}
