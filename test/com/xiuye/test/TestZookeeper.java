package com.xiuye.test;

import com.xiuye.sharp.X;
import org.apache.zookeeper.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class TestZookeeper {


//    @Test
    public void test1(){
        CountDownLatch connectedSemphore = new CountDownLatch(1);


        try {
            ZooKeeper zk = new ZooKeeper("106.13.163.111:2181", 5000,
                    e->{
                        X.lnS("Received watched event:",e);
                        if(Watcher.Event.KeeperState.SyncConnected == e.getState()){
                            connectedSemphore.countDown();
                        }
                    });
            X.lnS(zk.getState());
            connectedSemphore.await();
        }
        catch (InterruptedException| IOException e){
            e.printStackTrace();

        }
        X.lnS("ZooKeeper session established");

    }

//    @Test
    public void testSessionPasswd(){
        try {
            CountDownLatch connectedSemphore = new CountDownLatch(1);
            Watcher w = e->{
                X.lnS("Received watched event:",e);
                if(Watcher.Event.KeeperState.SyncConnected == e.getState()){
                    connectedSemphore.countDown();
                }
            };
            ZooKeeper zk = new ZooKeeper("106.13.163.111:2181", 5000, w);
            long sessionId = zk.getSessionId();
            byte[] passwd = zk.getSessionPasswd();
            zk = new ZooKeeper("106.13.163.111:2181", 5000, w,1l,"test".getBytes());
            zk = new ZooKeeper("106.13.163.111:2181", 5000, w,sessionId,passwd);
            Thread.sleep(Integer.MAX_VALUE);
        }
        catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

//    @Test
    public void addNode(){
        try {

            CountDownLatch connectedSemphore = new CountDownLatch(1);
            Watcher w = e->{
                X.lnS("Received watched event:",e);
                if(Watcher.Event.KeeperState.SyncConnected == e.getState()){
                    connectedSemphore.countDown();
                }
            };
            ZooKeeper zk = new ZooKeeper("106.13.163.111:2181", 5000, w);
            connectedSemphore.await();
            String path1 = zk.create("/zk-test-ephemeral-","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            X.lnS(path1);
            String path2 = zk.create("/zk-test-ephemeral-","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

            X.lnS(path2);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    @Test
    public void addNodeAsync(){
        try {

            CountDownLatch connectedSemphore = new CountDownLatch(1);
            Watcher w = e->{
                X.lnS("Received watched event:",e);
                if(Watcher.Event.KeeperState.SyncConnected == e.getState()){
                    connectedSemphore.countDown();
                }
            };
            AsyncCallback.StringCallback c= (rc,path,ctx,name)->{
                X.lnS("create path result:",rc,",",path,",",ctx,",real path name:",name);
            };

            ZooKeeper zk = new ZooKeeper("106.13.163.111:2181", 5000, w);
            connectedSemphore.await();
            zk.create("/zk-test-ephemeral-","".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                    c,
                    "I am context."
                    );
            zk.create("/zk-test-ephemeral-","".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
                    c,
                    "I am context."
                    );

            Thread.sleep(Integer.MAX_VALUE);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
