/**
 * 
 */
package com.zzia.wngn.zookeeper.client;


/**
 * @title 抽象zookeeper客户端类
 * @author Wang 2014年12月28日
 * @Date    2014年12月28日 下午10:51:50
 * @Version 1.0
 * @Description
 */
public abstract class AbstractZookeeperClient implements IZookeeperClient {
    
    /**
     * 设置zk服务器访问地址
     * 
     * @param zkConnectAddresses zk服务器访问地址
     * @throws Exception 设置zk服务器访问地址异常
     */
    public abstract void setZookeeperClient(String zkConnectAddresses) throws Exception;
    
  
}
