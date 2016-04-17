package com.xin.zk.client;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张新 on 2016/4/10.
 */
public class ZKOpSample {
    private ZooKeeper zk = null;

    public ZKOpSample(String connectString) {
        try {
            zk = new ZooKeeper(connectString, 6000, null);
        } catch (IOException e) {
            e.printStackTrace();
            if (zk != null) {
                try {
                    zk.close();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public String testCreateNode(String path, byte[] data, List<ACL> acls) {
        String res = "";
        try {
            // zk.addAuthInfo("auth", "javaclient1:111111".getBytes());
            res = zk.create(path, data, acls, CreateMode.PERSISTENT);
            System.out.println("创建节点" + res + "成功");
        } catch (KeeperException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 异步删除节点
     *
     * @param path
     * @return
     */
    public boolean deleteNodeWithAsync(String path,int version) {
        String context ="上下文对象测试";
        System.out.println("删除。");
        zk.delete(path, version, new DeleteCallBack(), context);
        return true;
    }

    public List<ACL> getIpAcl() {
        List<ACL> acls = new ArrayList<ACL>();
        Id ipId = new Id("ip", "192.168.1.100");
        acls.add(new ACL(ZooDefs.Perms.ALL, ipId));
        return acls;
    }

    public List<ACL> getDigestAcl() {
        List<ACL> acls = new ArrayList<ACL>();
        Id digestId = new Id("digest",
                "javaclient2:CGf2ylBdcKMdCYuzd08lQfOPvN0=");
        acls.add(new ACL(ZooDefs.Perms.ALL, digestId));
        return acls;
    }

}
