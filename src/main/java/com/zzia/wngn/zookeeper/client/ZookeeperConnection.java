/**
 * 
 */
package com.zzia.wngn.zookeeper.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zzia.wngn.util.ConfigUtil;


/**
 * @Company BONC
 * @Project BONC Data Integration
 * @Version 1.0
 * @Author  xiyunfeng
 * @Date    2014年9月25日 下午5:08:31
 * @Description zookeeper客户端工具类
 */
public class ZookeeperConnection {
	
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperConnection.class);
    
    /** zk客户端 */
    private static IZookeeperClient zookeeperClient;
    private static String zkConnectStr = "127.0.0.1:2181";
    static {
        String zkConnectStr_ = ConfigUtil.getStringValue("zkconnectstr");
        if(zkConnectStr_!=null){
        	zkConnectStr = zkConnectStr_;
        }
        logger.info("开始启动zookeeper["+zkConnectStr+"]");
        try {
            zookeeperClient = ZookeeperClientFactory.createZookeeperClient(zkConnectStr);
            zookeeperClient.start();
            logger.info("启动zookeeper["+zkConnectStr+"]成功！");
        } catch (Exception e) {
            logger.error("启动zookeeper["+zkConnectStr+"]失败:\n", e);
            System.exit(1);
        }
    }
    
    /**
     * 获得zookeeper客户端
     * 
     * @return zookeeper客户端
     */
    public static IZookeeperClient getConnect() {
        return zookeeperClient;
    }
    
    /**
     * 获得zookeeper客户端
     * 
     * @return zookeeper客户端
     */
    public static IZookeeperClient getZookeeperClient() {
        return zookeeperClient;
    }
    
    /**
     * 关闭客户端
     * 
     * @throws Exception 关闭客户端异常
     */
    public static void close() throws Exception {
        zookeeperClient.close();
    }

}
