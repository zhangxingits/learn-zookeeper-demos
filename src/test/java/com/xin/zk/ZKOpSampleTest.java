package com.xin.zk;

import com.xin.zk.client.ZKOpSample;
import org.apache.zookeeper.ZooDefs;
import org.junit.Test;

/**
 * Created by 张新 on 2016/4/10.
 */
public class ZKOpSampleTest {

    /**
     * 创建所有人都可以访问的节点
     */
    @Test
    public void testCreateNode() {
        new ZKOpSample("lab01:2181").
                testCreateNode("/acl/javaclient/node1", "ae86".getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE);
    }

    /**
     * 只有被认证过的人都可以访问
     */
    @Test
    public void testAuthNode() {
        ZKOpSample zks = new ZKOpSample("lab01:2181");
        zks.getZk().addAuthInfo("digest", "javaclient1:111111".getBytes());
        zks.testCreateNode("/acl/javaclient/node2",
                "node2data".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL);
    }

    /**
     * 创建指定ip访问的节点
     */
    @Test
    public void testAclIps() {
        ZKOpSample zks = new ZKOpSample("lab01:2181");
        zks.testCreateNode("/acl/javaclient/node3",
                "node3data".getBytes(), zks.getIpAcl());
    }

    /**
     * 异步删除节点测试
     */
    @Test
    public void testAsyncDelNode(){
        try {
            ZKOpSample zks = new ZKOpSample("lab01:2181");
            zks.getZk().addAuthInfo("digest", "javaclient1:111111".getBytes());
            zks.deleteNodeWithAsync("/acl/javaclient/node4", 0);

            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
