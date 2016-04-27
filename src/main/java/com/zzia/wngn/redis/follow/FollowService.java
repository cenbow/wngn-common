package com.zzia.wngn.redis.follow;

import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * @title 关注服务接口
 * @author wanggang
 * @date 2016年4月27日 下午5:43:17
 * @email wanggang@vfou.com
 * @descripe 使用redis的set数据结构实现关注功能，每个用户都要两张列表：粉丝列表和关注列表
 * <p>使用的redis命令如下：
 * <ul>
 * <li>sadd      添加元素    sadd key value</li>
 * <li>srem      删除元素    srem key value</li>
 * <li>scard     元素总数    scard key</li>
 * <li>smembers  元素集合    smembers key</li>
 * <li>sismember 存在元素    sismember key value</li>
 * <li>sinter    集合并集    sinter key key ...</li>
 * </ul>
 */
public interface FollowService {

    public void setRedis(Jedis redis);

    /**
     *  关注用户：sourceUserId关注targetUserId
     * @param sourceUserId 去关注的用户ID（关注动作发起者）
     * @param targetUserId 被关注的用户ID（关注动作承受者）
     */
    public void follow(String sourceUserId, String targetUserId);

    /**
     * 取消关注：sourceUserId取消关注targetUserId
     * @param sourceUserId 去关注的用户ID（关注动作发起者）
     * @param targetUserId 被关注的用户ID（关注动作承受者）
     */
    public void unFollow(String sourceUserId, String targetUserId);

    /**
     * 获取指定用户所有关注对象的用户编号集合
     * @param userId 指定用户id
     * @return
     */
    public Set<String> getFollowSet(String userId);

    /**
     * 取当前用户被哪些人关注的用户编号集合(粉丝列表)
     * @param userId 指定用户id
     * @return
     */
    public Set<String> getFansSet(String userId);

    /**
     * 是否关注指定用户，判断某用户是否关注了某用户，判断sourceUserId是否关注了targetUserId
     * @param sourceUserId 某用户ID（判断动作发起者）
     * @param targetUserId 某用户ID（判断动作承受者）
     * @return
     */
    public boolean isfollowUser(String sourceUserId, String targetUserId);

    /**
     * 是否有指定用户的粉丝,判断是否存在对应用户编号的粉丝方法实现，判断targetUserId是否是sourceUserId的粉丝
     * @param sourceUserId 某用户ID（判断动作发起者）
     * @param targetUserId 某用户ID（判断动作承受者）
     * @return
     */
    public boolean isFansUser(String sourceUserId, String targetUserId);

    /**
     * 统计指定用户关注的人数总和
     * @param userId 指定用户id
     * @return
     */
    public Long getFollowCount(String userId);

    /**
     * 统计指定用户粉丝人数总和
     * @param userId 指定用户id
     * @return
     */
    public Long getFansCount(String userId);

    /**
     * 获取指定用户和传入用户共同关注的用户集合
     * @param sourceUserId 指定用户 
     * @param targetUserId 传入用户 
     * @return
     */
    public Set<String> getCommonFollowSet(String sourceUserId, String targetUserId);

    /**
     * 获取指定用户和传入用户共同粉丝的用户集合
     * @param sourceUserId 指定用户 
     * @param targetUserId 传入用户 
     * @return
     */
    public Set<String> getCommonFansSet(String sourceUserId, String targetUserId);

    /**
     * 获取指定用户互粉的用户集合
     * @param userId 指定用户id
     * @return
     */
    public Set<String> getFollowFansSet(String userId);
}
