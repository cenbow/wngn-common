/**
 * @by WangGang @2015年1月2日
 */
package com.zzia.wngn.zookeeper.lock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import com.zzia.wngn.zookeeper.client.ZookeeperConnection;

/**
 * @title 分布式锁
 * @author Wang 2015年1月2日
 * @Date 2015年1月2日 下午9:34:38
 * @Version 1.0
 * @Description
 */
public class ZooDistributedBusywaitLock implements ZooDistributedLock,Watcher {
	private String root = "/locks";// 根
	private String lockName;// 竞争资源的标志
	private String waitNode;// 等待前一个锁
	private String myZnode;// 当前锁
	private CountDownLatch latch;// 计数器
	private int sessionTimeout = 30000;
	private CuratorFramework client;
	/**
	 * 创建分布式锁
	 * 
	 * @param lockName
	 *            竞争资源标志,lockName中不能包含单词lock
	 */
	public ZooDistributedBusywaitLock(String lockName) {
		this.lockName = lockName;
		try {
			this.client = (CuratorFramework)ZookeeperConnection.getZookeeperClient().getClient();
			this.client.checkExists().usingWatcher(this).forPath("/bonc");
			Stat stat = this.client.checkExists().forPath(root);
			if (stat == null) {
				this.client.create().withMode(CreateMode.PERSISTENT).forPath(root);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * zookeeper节点的监视器
	 */
	public void process(WatchedEvent event) {
		if (this.latch != null) {
			System.out.println("lock " + myZnode);
			this.latch.countDown();
		}else{
			System.out.println("this.latch == null");
		}
	}

	@Override
	public boolean lock() {
		try {
			if (this.tryLock()) {
				System.out.println("Thread " + Thread.currentThread().getId() + " " + myZnode + " get lock true");
				return true;
			} else {
				return waitForLock(waitNode, sessionTimeout);// 等待锁
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; 
	}

	public boolean tryLock() {
		try {
			String splitStr = "_lock_";
			if (lockName.contains(splitStr)){
				throw new Exception("lockName can not contains "+splitStr);
			}
			// 创建临时子节点
			myZnode = this.client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(root + "/" + lockName + splitStr);
			System.out.println(myZnode + " is created ");
			// 取出所有子节点
			List<String> subNodes = this.client.getChildren().forPath(root);
			// 取出所有lockName的锁
			List<String> lockObjNodes = new ArrayList<String>();
			for (String node : subNodes) {
				String _node = node.split(splitStr)[0];
				if (_node.equals(lockName)) {
					lockObjNodes.add(node);
				}
			}
			Collections.sort(lockObjNodes);
			System.out.println(myZnode + "==" + lockObjNodes.get(0));
			if (myZnode.equals(root + "/" + lockObjNodes.get(0))) {
				// 如果是最小的节点,则表示取得锁
				return true;
			}
			// 如果不是最小的节点，找到比自己小1的节点
			String subMyZnode = myZnode.substring(myZnode.lastIndexOf("/") + 1);
			waitNode = lockObjNodes.get(Collections.binarySearch(lockObjNodes, subMyZnode) - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean tryLock(long time, TimeUnit unit) {
		try {
			if (this.tryLock()) {
				return true;
			}
			return waitForLock(waitNode, time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean waitForLock(String lower, long waitTime) throws Exception {
		Stat stat = this.client.checkExists().usingWatcher(this).forPath(root + "/" + lower);
		// 判断比自己小一个数的节点是否存在,如果不存在则无需等待锁,同时注册监听
		if (stat != null) {
			System.out.println("Thread " + Thread.currentThread().getId() + " waiting for " + root + "/" + lower);
			this.latch = new CountDownLatch(1);
			boolean result = this.latch.await(waitTime, TimeUnit.MILLISECONDS);
			this.latch = null;
			if(result){
				return true;
			}
		}
		return false;
	}

	@Override
	public void unlock() {
		try {
			System.out.println("unlock " + myZnode);
			this.client.delete().forPath(myZnode);
			myZnode = null;
			//ZKClientUtil.getZKClient().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
