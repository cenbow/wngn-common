package com.zzia.wngn.redis.access;

import redis.clients.jedis.Jedis;

/**
 * @title 访问控制服务
 * @author wanggang
 * @date 2016年4月28日 上午11:06:32
 * @email wanggang@vfou.com
 * @descripe
 */
public interface AccessService {

    public void setRedis(Jedis redis);

    /**
     * 访问控制：每秒访问10次的简单控制
     * <p>
     * 设置redis的key的生存时间为60秒，每次访问值加一，大于10时，访问拒绝
     * <p>使用的redis命令如下：
     * <ul>
     * <li>set      添加元素    set key value</li>
     * <li>expire   生存时间    expire key times</li>
     * <li>exists   元素存在    exists key</li>
     * </ul>
     * @param ip 访问IP
     * @return
     */
    public boolean ValidateSimpleAccess(String ip);

    /**
     * 访问控制：每秒访问10次的升级控制
     * <p>
     * 设置redis的队列，值为当前时间，每次访问添加一个值，列表长度大于10时，
     * 判断当前时间减去表头元素值是否大于60秒，小于访问拒绝，大于则将表头元素删除，新增元素
     * <p>使用的redis命令如下：
     * <ul>
     * <li>lpush    添加元素    lpush key value</li>
     * <li>llen     列表长度    llen key</li>
     * <li>lindex   获取元素    lindex key index</li>
     * <li>ltrim    留存元素    ltrim key start stop</li>
     * </ul>
     * @param ip 访问IP
     * @return
     */
    public boolean ValidateEnhanceAccess(String ip);

}
