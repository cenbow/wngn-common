/**
 * 
 */
package com.zzia.wngn.zookeeper.client;


/**
 * @Company BONC
 * @Project BONC Data Integration
 * @Version 1.0
 * @Author  xiyunfeng
 * @Date    2014年9月22日 下午6:58:10
 * @Description 节点数据类型 
 */
public class ZookeeperNode {
    
    /** 节点路径 */
    private String path;
    
    /** 数据 */
    private String data;

    
    public String getPath() {
        return path;
    }

    
    public void setPath(String path) {
        this.path = path;
    }

    
    public String getData() {
        return data;
    }

    
    public void setData(String data) {
        this.data = data;
    }
    

}
