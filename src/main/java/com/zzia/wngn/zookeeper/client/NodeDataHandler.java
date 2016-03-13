/**
 * 
 */
package com.zzia.wngn.zookeeper.client;

import java.util.HashMap;
import java.util.Map;


/**
 * @title 节点数据工具类
 * @author Wang 2014年12月29日
 * @Date    2014年12月29日 上午12:32:21
 * @Version 1.0
 * @Description
 */
public class NodeDataHandler {
    
    /** 数据条目内部分隔符 */
    private static String DATA_ITEM_SEPARATOR = ":";
    
    /** 数据条目之间分隔符 */
    private static String DATA_ITEMS_SEPARATOR = ";";
    
    /**
     * 解析节点字符串数据成Map形式
     * 
     * @param nodeDataStr 节点数据
     * @return Map形式的节点数据
     */
    public static Map<String, String> parseNodeData(String nodeDataStr) {
        String[] dataItems = nodeDataStr.split(DATA_ITEMS_SEPARATOR);
        
        String[] keyValues;   
        Map<String, String> result = new HashMap<String,String>();
        
        for(String dataItem:dataItems) {
            keyValues = dataItem.split(DATA_ITEM_SEPARATOR);
            result.put(keyValues[0], keyValues.length>1?keyValues[1]:"");
        }
        
        return result;
    }
    
    /**
     * 获得String形式的节点数据
     * 
     * @param nodeDataMap Map形式的节点数据
     * @return String形式的节点数据
     */
    public static String getNodeDataStr(Map<String, String> nodeDataMap) {
        StringBuilder result = new StringBuilder();
        for( Map.Entry<String, String> data:nodeDataMap.entrySet() ) {
            result.append( data.getKey() );
            result.append( DATA_ITEM_SEPARATOR);
            result.append( data.getValue() );
            result.append( DATA_ITEMS_SEPARATOR );
        }
        
        result.deleteCharAt( result.length()-1 );
        return result.toString();
    }
    
}
