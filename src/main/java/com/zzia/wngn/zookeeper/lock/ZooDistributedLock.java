/**
 * @by WangGang @2015年1月4日
 */
package com.zzia.wngn.zookeeper.lock;

/**
 * @title 
 * @author Wang 2015年1月4日
 * @Date    2015年1月4日 下午11:44:49
 * @Version 1.0
 * @Description  
 */
public interface ZooDistributedLock {
	public final static String LOCK_MUTEX ="mutex";
	public final static String LOCK_BUSYWAIT ="busywait";
	/**
	 * 加锁
	 * @return
	 */
	public boolean lock();
	
	/**
	 * 释放锁
	 */
	public void unlock();
}
