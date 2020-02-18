package com.zhangjie.zookeeper.study;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistributeClient {
    private ZooKeeper zkClient;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        DistributeClient client = new DistributeClient();
        // 1 获取ZK集群连接
        client.getConnect();
        // 2 注册监听
        //client.getChildren();
        // 3 业务逻辑处理
        client.bussiness();
    }

    private void bussiness() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }
    private void getChildren() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/servers",true);
        // 存储服务器节点主机名称集合
        List<String> hosts = new ArrayList<String>();
        System.out.println("############# start #############");
        for(String child : children){
            byte[] data = zkClient.getData("/servers/" + child, false, null);
            hosts.add(new String(data));
        }
        System.out.println(hosts);
        System.out.println("############# end #############");
    }

    /**
     * 获取zk的客户端
     * @throws IOException
     */
    private void getConnect() throws IOException {
        String cluster = "node2:2181,node3:2181,node4:2181";
        int sessionTimeout = 2000;
        zkClient = new ZooKeeper(cluster, sessionTimeout, watcher -> {
            try {
                getChildren();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
