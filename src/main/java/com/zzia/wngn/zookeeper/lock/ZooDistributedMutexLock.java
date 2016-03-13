/**
 * @by WangGang @2015年1月4日
 */
package com.zzia.wngn.zookeeper.lock;

import java.util.UUID;

import com.zzia.wngn.zookeeper.client.ZookeeperConnection;

/**
 * @title zk分布式锁
 * @author Wang 2015年1月4日
 * @Date    2015年1月4日 下午11:48:16
 * @Version 1.0
 * @Description  对于持久化节点，如果所释放失败，那么就会造成死锁，给锁一个有效时间，超过这段时间，锁失效
 */
public class ZooDistributedMutexLock implements ZooDistributedLock {

	private static String LOCK_PATH="/bonc/bdi/lock/";
	private static int LOCK_VALID_TIME =120*1000;
	private String resource;
	
	public ZooDistributedMutexLock(String resource) {
		this.resource=resource;
	}

	@Override
	public boolean lock(){
		try {
			if(ZookeeperConnection.getZookeeperClient().checkExists(LOCK_PATH+this.resource)){
				if(validateLock(ZookeeperConnection.getZookeeperClient().getStringData(LOCK_PATH+this.resource))){
					return false;
				}else{
					ZookeeperConnection.getZookeeperClient().deleteNode(true, LOCK_PATH+this.resource);
				}
			}
			ZookeeperConnection.getZookeeperClient().createPersistentNode(LOCK_PATH+this.resource,createUuid()+"_"+System.currentTimeMillis());
			return true;
		} catch (Exception e) {
			System.out.println("创建锁"+LOCK_PATH+this.resource+"异常");
			e.printStackTrace();
		}
		return false;
		
	}

	@Override
	public void unlock(){
		try {
			ZookeeperConnection.getZookeeperClient().deleteNode(true, LOCK_PATH+resource);
		} catch (Exception e) {
			System.out.println("释放锁"+LOCK_PATH+this.resource+"失败！");
			e.printStackTrace();
		}
	}
	
	public String createUuid(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
	
	private boolean validateLock(String dataStr){
		if(System.currentTimeMillis()-Long.parseLong(dataStr.substring(dataStr.indexOf("_")+1))<LOCK_VALID_TIME){
			return true;
		}
		return false;
		
	}

}
