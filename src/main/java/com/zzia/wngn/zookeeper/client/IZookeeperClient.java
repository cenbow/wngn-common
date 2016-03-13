/**
 * 
 */
package com.zzia.wngn.zookeeper.client;

import java.util.List;


/**
 * @title zookeeper客户端操作接口
 * @author Wang 2014年12月28日
 * @Date    2014年12月28日 下午10:50:50
 * @Version 1.0
 * @Description
 */
public interface IZookeeperClient {
    
    /**
     * 启动zookeeper客户端
     */
    public void start() throws Exception;
    
    public Object getClient() throws Exception;
    
    /**
     * 关闭zookeeper客户端
     */
    public void close() throws Exception;
    
    /**
     * 创建节点
     * 
     * @param path 节点路径(例如：/bonc/bdi/pipe)
     * @throws Exception 创建节点异常
     */
    public void createPersistentNode(String path) throws Exception;
    
    /**
     * 创建带数据节点
     * 
     * @param path 节点路径
     * @param data 数据
     * @throws Exception 创建节点异常
     */
    public void createPersistentNode(String path, byte[] data) throws Exception;
    
    /**
     * 创建带数据节点（数据为String型）
     * 
     * @param path 节点路径
     * @param data 数据
     * @throws Exception 创建节点异常
     */
    public void createPersistentNode(String path, String stringData) throws Exception;
    
    /**
     * 创建带数据子节点
     * 
     * @param isCreatParent 是否创建父节点
     * @param parentPath 父节点路径
     * @param childNodes 子节点集合
     */
    public void createPersistentChildNodes(boolean isCreatParent, String parentPath, List<ZookeeperNode> childNodes) throws Exception;
    
    /**
     * 创建带数据的临时节点
     * @param parentPath 父节点路径
     * @param nodeName	临时节点名称
     * @param stringData 临时节点数据
     * @throws Exception
     */
    
    public void createEphemeralNodes(String parentPath, String nodeName,String stringData) throws Exception;
    
    /**
     * 删除节点
     * 
     * @param isDeleteChild 是否删除子节点 
     * @param path 节点路径
     * @throws Exception 删除节点异常
     */
    public boolean deleteNode(boolean isDeleteChild, String path) throws Exception; 
    
    /**
     * 设置节点数据
     * 
     * @param path 节点路径
     * @param stringData  数据
     * @throws Exception 设置节点数据异常
     */
    public void setStringData(String path, String stringData) throws Exception; 
    
    /**
     * 设置节点数据
     * 
     * @param path 节点路径
     * @param data 数据
     * @throws Exception 设置节点数据异常
     */
    public void setData(String path, byte[] data) throws Exception; 
      
    /**
     * 检测节点是否存在
     * 
     * @param path 节点路径
     * @throws Exception 检测节点异常
     */
    public boolean checkExists(String path) throws Exception;
    
    /**
     * 获取节点中的数据
     * 
     * @param path 节点路径
     * @return 节点上的数据
     * @throws Exception 获取节点中的数据异常
     */
    public String getStringData(String path) throws Exception;
    
    /**
     * 获取节点中的数据
     * 
     * @param path 节点路径
     * @return 节点上的数据
     * @throws Exception 获取节点中的数据异常
     */
    public byte[] getData(String path) throws Exception;
    
    /**
     * 获取子节点集合
     * 
     * @param path 节点路径
     * @return 子节点集合
     * @throws Exception 获取子节点异常
     */
    public List<String> getChildNodeNames(String parentPath) throws Exception;
    
    /**
     * 获取子节点集合
     * 
     * @param parentPath 父节点路径
     * @throws Exception 获取子节点集合异常
     */
    public List<ZookeeperNode> getChildNodes(String parentPath) throws Exception;   
    
    /**
     * 将源目标拷贝到目标路径（只支持拷贝一级下子节点，不支持递归拷贝）
     * 
     * @param sourcePath 源目标路径
     * @param targetPath 目标路径
     * @throws Exception 拷贝异常
     */
    public void copy(String sourcePath, String targetPath) throws Exception; 
}
