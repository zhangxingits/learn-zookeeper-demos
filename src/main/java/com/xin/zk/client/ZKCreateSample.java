package com.xin.zk.client;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * Created by 张新 on 2016/4/10.
 */
public class ZKCreateSample implements Watcher{
    private static CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) {
        ZooKeeper zooKeeper=null;
        try {
            zooKeeper= new ZooKeeper("192.168.1.168:2181",6000,new ZKCreateSample());
            System.out.println("begin state="+zooKeeper.getState());
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Zookeeper session established.");
        }finally {
            if (zooKeeper != null) {
                try{
                    zooKeeper.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        System.out.println("end state="+zooKeeper.getState());
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("receive watched event: "+watchedEvent);
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            latch.countDown();
        }
    }
}
