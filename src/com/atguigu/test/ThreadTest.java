package com.atguigu.test;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadTest{

	public static void main(String[] args) {
		
		final MyQueue myQueue = new MyQueue();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				myQueue.set(new Random().nextInt(20));
			}
		},"AA").start();
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i = 1; i <= 100; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					myQueue.get();
				}
			},String.valueOf(i)).start();
		}
		
	}
	
}

class MyQueue{

	private Object obj;
	ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	public void set(Object obj) {
		readWriteLock.writeLock().lock();
		
		try {
			this.obj = obj;
			System.out.println(Thread.currentThread().getName()+"\t"+obj);
		} catch (Exception e) {
		}finally {
			readWriteLock.writeLock().unlock();
		}
	}

	public void get() {
		readWriteLock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName()+"\t"+obj);
		} catch (Exception e) {
		}finally {
			readWriteLock.readLock().unlock();
		}
	}
	
}
