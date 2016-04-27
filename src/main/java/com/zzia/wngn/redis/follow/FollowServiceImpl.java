package com.zzia.wngn.redis.follow;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @title 关注服务的实现类
 * @author wanggang
 * @date 2016年4月27日 下午6:32:45
 * @email wanggang@vfou.com
 * @descripe 通过redis实现关注的具体方式
 * <p>
 * <strong>Example:</strong>
 * <blockquote>
 * <pre>
 * FollowService followService = new FollowServiceImpl();
 * Jedis redis = new Jedis("127.0.0.1", 6379);
 * followService.setRedis(redis);
 *
 * followService.follow("1", "2");
 * System.out.println("用户1关注用户2");
 * System.out.println("用户1的关注列表：" + followService.getFollowSet("1"));
 * System.out.println("用户2的粉丝列表：" + followService.getFansSet("2"));

 * followService.follow("1", "3");
 * System.out.println("用户1关注用户3");
 * System.out.println("用户1的关注列表：" + followService.getFollowSet("1"));
 * System.out.println("用户3的粉丝列表：" + followService.getFansSet("3"));

 * followService.follow("2", "1");
 * System.out.println("用户2关注用户1");
 * System.out.println("用户2的关注列表：" + followService.getFollowSet("2"));
 * System.out.println("用户1的粉丝列表：" + followService.getFansSet("1"));

 * followService.follow("2", "3");
 * System.out.println("用户2关注用户3");
 * System.out.println("用户2的关注列表：" + followService.getFollowSet("2"));
 * System.out.println("用户3的粉丝列表：" + followService.getFansSet("3"));

 * followService.follow("3", "1");
 * System.out.println("用户3关注用户1");
 * System.out.println("用户3的关注列表：" + followService.getFollowSet("3"));
 * System.out.println("用户1的粉丝列表：" + followService.getFansSet("1"));

 * followService.follow("3", "2");
 * System.out.println("用户3关注用户2");
 * System.out.println("用户3的关注列表：" + followService.getFollowSet("3"));
 * System.out.println("用户2的粉丝列表：" + followService.getFansSet("2"));

 * System.out.println("----------------");
 * System.out.println("用户1的互粉列表：" + followService.getFollowFansSet("1"));
 * System.out.println("用户2的互粉列表：" + followService.getFollowFansSet("2"));
 * System.out.println("用户3的互粉列表：" + followService.getFollowFansSet("3"));
 * System.out.println("----------------");
 * System.out.println("用户1的关注数和粉丝数：" + followService.getFollowCount("1") + ":" + followService.getFansCount("1"));
 * System.out.println("用户2的关注数和粉丝数：" + followService.getFollowCount("2") + ":" + followService.getFansCount("2"));
 * System.out.println("用户3的关注数和粉丝数：" + followService.getFollowCount("3") + ":" + followService.getFansCount("3"));

 * followService.unFollow("1", "2");
 * followService.unFollow("1", "3");

 * followService.unFollow("2", "1");
 * followService.unFollow("2", "3");

 * followService.unFollow("3", "1");
 * followService.unFollow("3", "2");
 * </pre>
 * </blockquote>
 */
public class FollowServiceImpl implements FollowService {

    private Jedis redis;

    public void setRedis(Jedis redis) {
        this.redis = redis;
    }

    @Override
    public void follow(String sourceUserId, String targetUserId) {
        Transaction tx = this.redis.multi();
        tx.sadd("graph:user:" + sourceUserId + ":follow", targetUserId);
        tx.sadd("graph:user:" + targetUserId + ":fans", sourceUserId);
        tx.exec();
    }

    @Override
    public void unFollow(String sourceUserId, String targetUserId) {
        Transaction tx = this.redis.multi();
        tx.srem("graph:user:" + sourceUserId + ":follow", targetUserId);
        tx.srem("graph:user:" + targetUserId + ":fans", sourceUserId);
        tx.exec();
    }

    @Override
    public Set<String> getFollowSet(String userId) {
        return this.redis.smembers("graph:user:" + userId + ":follow");
    }

    @Override
    public Set<String> getFansSet(String userId) {
        return this.redis.smembers("graph:user:" + userId + ":fans");
    }

    @Override
    public boolean isfollowUser(String sourceUserId, String targetUserId) {
        return this.redis.sismember("graph:user:" + sourceUserId + ":follow", targetUserId);
    }

    @Override
    public boolean isFansUser(String sourceUserId, String targetUserId) {
        return this.redis.sismember("graph:user:" + sourceUserId + ":fans", targetUserId);
    }

    @Override
    public Long getFollowCount(String userId) {
        return this.redis.scard("graph:user:" + userId + ":follow");
    }

    @Override
    public Long getFansCount(String userId) {
        return this.redis.scard("graph:user:" + userId + ":fans");
    }

    @Override
    public Set<String> getCommonFollowSet(String sourceUserId, String targetUserId) {
        return this.redis.sinter("graph:user:" + sourceUserId + ":follow", "graph:user:" + targetUserId + ":follow");
    }

    @Override
    public Set<String> getCommonFansSet(String sourceUserId, String targetUserId) {
        return this.redis.sinter("graph:user:" + sourceUserId + ":fans", "graph:user:" + targetUserId + ":fans");
    }

    @Override
    public Set<String> getFollowFansSet(String userId) {
        return this.redis.sinter("graph:user:" + userId + ":follow", "graph:user:" + userId + ":fans");
    }

}
