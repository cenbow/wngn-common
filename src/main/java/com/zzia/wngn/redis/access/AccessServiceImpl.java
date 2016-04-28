package com.zzia.wngn.redis.access;

import redis.clients.jedis.Jedis;

/**
 * @title 访问控制服务实现类
 * @author wanggang
 * @date 2016年4月28日 上午11:08:29
 * @email wanggang@vfou.com
 * @descripe 通过redis实现访问控制的具体方式
 * <p>
 * <strong>Example:</strong>
 * <blockquote>
 * <pre>
 * public static void main(String[] args) {
 *      test("simple"); // test("enhance");
 *  }
 *
 *  public static void test(String type) {
 *      AccessService accessService = new AccessServiceImpl();
 *      Jedis redis = new Jedis("127.0.0.1", 6379);
 *      accessService.setRedis(redis);
 *      String ip = "192.168.0.101";
 *      for (int i = 0; i < 40; i++) {
 *          if ("simple".equals(type) ? accessService.ValidateSimpleAccess(ip)
 *                  : accessService.ValidateEnhanceAccess(ip)) {
 *              System.out.println(i + "---" + ip + "：访问成功！--true");
 *          } else {
 *              System.out.println(i + "---" + ip + "：访问拒绝！--false");
 *          }
 *          if (i > 10 && i < 20) {
 *              try {
 *                  Thread.sleep(10 * 1000);
 *              } catch (InterruptedException e) {
 *                  e.printStackTrace();
 *              }
 *          }
 *          if (i == 20) {
 *              System.out.println("场景：暂停60s,初始化数据");
 *              try {
 *                  Thread.sleep(60 * 1000);
 *              } catch (InterruptedException e) {
 *                  e.printStackTrace();
 *              }
 *          }
 *          if (i == 21) {
 *              System.out.println("场景：该分钟的第一秒访问一次");
 *              try {
 *                  Thread.sleep(55 * 1000);
 *              } catch (InterruptedException e) {
 *                  e.printStackTrace();
 *              }
 *          }
 *
 *         if (i == 30) {
 *              System.out.println("场景：该分钟的最后一秒访问9次");
 *              try {
 *                  Thread.sleep(5 * 1000);
 *              } catch (InterruptedException e) {
 *                  e.printStackTrace();
 *              }
 *              System.out.println("场景：下一分钟的的第一秒访问9次");
 *          }
 *      }
 *
 *  }
 * </pre>
 * </blockquote>
 */
public class AccessServiceImpl implements AccessService {

    private Jedis redis;

    @Override
    public void setRedis(Jedis redis) {
        this.redis = redis;
    }

    @Override
    public boolean ValidateSimpleAccess(String ip) {
        String key = "rate:limit:" + ip;
        boolean exist = redis.exists(key);
        if (exist) {
            long times = redis.incr(key);
            if (times > 10) {
                return false;
            }
        } else {
            redis.set(key, "1");
            redis.expire(key, 60);
        }
        return true;
    }

    @Override
    public boolean ValidateEnhanceAccess(String ip) {
        String key = "rate:limit:" + ip;
        long length = redis.llen(key);
        if (length < 10) {
            redis.lpush(key, System.currentTimeMillis() + "");
        } else {
            String times = redis.lindex(key, 9);
            if (System.currentTimeMillis() - Long.parseLong(times) < 60 * 1000) {
                return false;
            } else {
                redis.lpush(key, System.currentTimeMillis() + "");
                redis.ltrim(key, 0, 9);
            }
        }
        return true;
    }

}
