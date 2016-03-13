/**
 * 
 */
package com.zzia.wngn.zookeeper.client;



/**
 * @Company BONC
 * @Project BONC Data Integration
 * @Version 1.0
 * @Author  xiyunfeng
 * @Date    2014年9月20日 下午3:47:21
 * @Description zookeeper客户端接口 
 */
public class ZookeeperClientFactory {
    
    /**
     * 创建zookeeper客户端
     * 
     * @param zkConnectAddresses zookeeper服务器访问地址
     * @return zookeeper客户端对象
     * @throws Exception 创建zookeeper客户端对象异常
     */
    public static IZookeeperClient createZookeeperClient(String zkConnectAddresses) throws Exception {
        String zkClientClassName ="com.zzia.wngn.zookeeper.client.CuratorZookeeperClient";
        
        AbstractZookeeperClient zookeeperClient = (AbstractZookeeperClient)Class.forName(zkClientClassName)
                                                                                .newInstance();
        
        zookeeperClient.setZookeeperClient(zkConnectAddresses);
        
        return zookeeperClient;
    }
}
