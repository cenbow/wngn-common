/**
 * @by WangGang @2015年1月17日
 */
package com.zzia.wngn.zookeeper.lock;

import com.zzia.wngn.zookeeper.lock.ConcurrentTest.ConcurrentTask;

public class ZooDistributedLockTest {
	public static int NUM = 0;
	
	public static void main(String[] args) {
		mutex();
		//busywait();
	}
	public static void mutex() {
		 Runnable task1 = new Runnable(){
	            public void run() {
	            	ZooDistributedLock lock = null;
	            	boolean clearLock = false;
	                try {
	                	lock = ZooDistributedLockService.getZooLockInstance(ZooDistributedLock.LOCK_MUTEX, "test3");
	                    if(lock.lock()){
	                    	clearLock = true;
	                    	System.out.println("===Thread " + Thread.currentThread().getId() + " running");
	                    	Thread.sleep(60*1000);
	                    }else{
	                    	System.out.println("加锁失败，锁已经存在");
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }finally {
	                    if(lock != null&&clearLock)
	                        lock.unlock();
	                }
	                 
	            }
	             
	        };
	        new Thread(task1).start();
	}
    public static void busywait() {
        Runnable task1 = new Runnable(){
            public void run() {
            	ZooDistributedLock lock = null;
                try {
                	lock = ZooDistributedLockService.getZooLockInstance(ZooDistributedLock.LOCK_BUSYWAIT, "test2");
                    lock.lock();
                    NUM++;
                    Thread.sleep(3000);
                    System.out.println("===Thread " + Thread.currentThread().getId() + " running");
                } catch (Exception e) {
                    e.printStackTrace();
                } 
                finally {
                    if(lock != null)
                        lock.unlock();
                }
                 
            }
             
        };
        new Thread(task1).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        ConcurrentTask[] tasks = new ConcurrentTask[60];
        for(int i=0;i<tasks.length;i++){
            ConcurrentTask task3 = new ConcurrentTask(){
                public void run() {
                	ZooDistributedLock lock = null;
                    try {
                    	lock = ZooDistributedLockService.getZooLockInstance(ZooDistributedLock.LOCK_BUSYWAIT, "test2");
                        lock.lock();
                        NUM++;
                        Thread.sleep(300);
                        System.out.println("Thread " + Thread.currentThread().getId() + " running");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } 
                    finally {
                        lock.unlock();
                    }
                     
                }
            };
            tasks[i] = task3;
        }
        new ConcurrentTest(tasks);
        
        System.out.println("NUM="+NUM);
    }
}
