/**
 * 
 */
package com.zzia.wngn.zookeeper.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;



/**
 * @Company BONC
 * @Project BONC Data Integration
 * @Version 1.0
 * @Author  xiyunfeng
 * @Date    2014年9月20日 下午3:55:51
 * @Description 对Curator进行包装的zookeeper客户端 
 */
public class CuratorZookeeperClient extends AbstractZookeeperClient {
    
    /** Curator 客户端*/
    private CuratorFramework client;
    
    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.AbstractZookeeperClient#setZookeeperClient(java.lang.String)
     */
    @Override
    public void setZookeeperClient(String zkConnectAddresses) throws Exception {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        
        this.client = CuratorFrameworkFactory.builder()
                                             .connectString(zkConnectAddresses)
                                             //.namespace("/bonc")
                                             .retryPolicy(retryPolicy)
                                             .connectionTimeoutMs(5000)
                                             //.sessionTimeoutMs(sessionTimeoutMs)
                                             .build();
    }
    
    @Override
	public Object getClient() throws Exception {
		// TODO Auto-generated method stub
		return this.client;
	}
    
    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#start()
     */
    @Override
    public void start() throws Exception {
        this.client.start();
    }
    
    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#close()
     */
    @Override
    public void close() throws Exception {
        this.client.close();
    }
    
    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#createPersistentNode(java.lang.String)
     */
    @Override
    public void createPersistentNode(String path) throws Exception {
        // 创建一个节点      
        this.client.create()
                   .creatingParentsIfNeeded()
                   .withMode(CreateMode.PERSISTENT)
                   .forPath(path);   
    }

    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#createPersistentNode(java.lang.String, byte[])
     */
    @Override
    public void createPersistentNode(String path, byte[] data) throws Exception {
        // 创建一个节点      
        this.client.create()
                   .creatingParentsIfNeeded()
                   .withMode(CreateMode.PERSISTENT)
                   .forPath(path, data);  
        
    }

    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#createPersistentNode(java.lang.String, java.lang.String)
     */
    @Override
    public void createPersistentNode(String path, String stringData) throws Exception {
        // 创建一个节点   
        this.client.create()
                   .creatingParentsIfNeeded()
                   .withMode(CreateMode.PERSISTENT)
                   .forPath(path, stringData.getBytes("UTF8")); 
        
    }
    
    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#createPersistentChildNodes(boolean, java.lang.String, java.util.List)
     */
    @Override
    public void createPersistentChildNodes(boolean isCreatParent, String parentPath, List<ZookeeperNode> childNodes) throws Exception {
        if(isCreatParent) {
            this.createPersistentNode(parentPath);
        }
        
        String nodeData;
        for(ZookeeperNode chidNode:childNodes) {
            nodeData = chidNode.getData();
            if(null != nodeData && !"".equals(nodeData) ) {
                this.createPersistentNode(parentPath+"/"+chidNode.getPath(), nodeData);
            } else {
                this.createPersistentNode(parentPath+"/"+chidNode.getPath());
            }
        }
    }
    
    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#setStringData(java.lang.String, java.lang.String)
     */
    @Override
    public void setStringData(String path, String stringData) throws Exception {
        this.client.setData()
                   .forPath(path, stringData.getBytes("UTF8"));
    }

    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#setData(java.lang.String, byte[])
     */
    @Override
    public void setData(String path, byte[] data) throws Exception {
        this.client.setData()
                   .forPath(path, data);
    }

    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#checkExists(java.lang.String)
     */
    @Override
    public boolean checkExists(String path) throws Exception {
        Stat nodeStat = this.client.checkExists()
                                   .forPath(path);
        
        if(null == nodeStat) {
            return false;
        } else {
            return true;
        }
        
    }


    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#getData(java.lang.String)
     */
    @Override
    public byte[] getData(String path) throws Exception {
        // 取数据 
        return this.client.getData()
                          .forPath(path); 
    }
    
    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#getStringData(java.lang.String)
     */
    @Override
    public String getStringData(String path) throws Exception {
        // 取数据 
        byte[] result =  this.client.getData()
                                    .forPath(path); 
        
        return new String(result, "UTF8");

    }


    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#getChildNodeNames(java.lang.String)
     */
    @Override
    public List<String> getChildNodeNames(String path) throws Exception {
        return this.client.getChildren()
                          .forPath(path);
    }

    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#deleteNode(boolean, java.lang.String)
     */
    @Override
    public boolean deleteNode(boolean isDeleteChild, String path) throws Exception {
        if(isDeleteChild) {
            this.client.delete()
                       .guaranteed()
                       .deletingChildrenIfNeeded()
                       .forPath(path);
        } else {
            this.client.delete()
                       .guaranteed()
                       .forPath(path);
        }
      
        return true;
    }

    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#getChildNodes(java.lang.String)
     */
    @Override
    public List<ZookeeperNode> getChildNodes(String parentPath) throws Exception {
        List<ZookeeperNode> childNodes = new ArrayList<ZookeeperNode>();
        ZookeeperNode childNode;
        List<String> childNodeNames = this.getChildNodeNames(parentPath);
        
        for(String childNodeName:childNodeNames) {
            childNode = new ZookeeperNode();
            childNode.setPath(childNodeName);
            childNode.setData(this.getStringData(parentPath+"/"+childNodeName));
            
            childNodes.add(childNode);
        }
        
        return childNodes;
    }

    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#copy(java.lang.String, java.lang.String)
     */
    @Override
    public void copy(String sourcePath, String targetPath) throws Exception {
        List<ZookeeperNode> sourceNodes = this.getChildNodes( sourcePath );
        this.createPersistentChildNodes(false, targetPath, sourceNodes);
    }
    /* (non-Javadoc)
     * @see com.bonc.dataplatform.bdi.common.zookeeper.IZookeeperClient#createEphemeralNodes(java.lang.String,java.lang.String, java.lang.String)
     */
	@Override
	public void createEphemeralNodes(String parentPath,
			String nodeName, String stringData) throws Exception {
		// 创建一个节点   
        this.client.create()
                   .creatingParentsIfNeeded()
                   .withMode(CreateMode.EPHEMERAL)
                   .forPath(parentPath+"/"+nodeName, stringData.getBytes("UTF8")); 
	}
	
	public void createNodes(String parentPath,
			String nodeName, String stringData) throws Exception {
		// 创建一个节点   
        this.client.create()
                   .creatingParentsIfNeeded()
                   .withMode(CreateMode.EPHEMERAL)
                   .forPath(parentPath+"/"+nodeName, stringData.getBytes("UTF8")); 
        this.client.checkExists().watched().forPath("");
	}

}
