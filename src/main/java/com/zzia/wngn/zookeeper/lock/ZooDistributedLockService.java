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
public class ZooDistributedLockService {

	public static ZooDistributedLock getZooLockInstance(String lockMode,String resource){
		if(ZooDistributedLock.LOCK_MUTEX.equals(lockMode)){
			return new ZooDistributedMutexLock(resource);
		}else if(ZooDistributedLock.LOCK_BUSYWAIT.equals(lockMode)){
			return new ZooDistributedBusywaitLock(resource);
		}
		return null;
		
	}
}
