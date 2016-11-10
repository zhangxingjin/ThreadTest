package com.atguigu.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadExe {

	/**
	 * 题目：现在两个线程，可以操作同一个变量， 实现一个线程对该变量加1，一个线程对该变量减1， 实现交替，来10轮，变量初始值为零。
	 * 
	 * @author admin
	 */
	public static void main(String[] args) {
		final Number n = new Number();

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					n.increase();
				}
			}
		}, "线程1").start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					n.decrease();
				}
			}
		}, "线程2").start();
	}

}

class Number {

	int num = 0;
	Lock lock = new ReentrantLock();
	Condition condition1 = lock.newCondition();

	public void increase() {
		
		lock.lock();
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			while(num != 0) {
				condition1.await();
			}

			num++;
			System.out.println(Thread.currentThread().getName() + "：" + num);
			
			condition2.signal();
			
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
	}
	

	Condition condition2 = lock.newCondition();
	public  void decrease() {
		
		lock.lock();
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			while(num == 0) {
				condition2.await();
			}

			num--;
			System.out.println(Thread.currentThread().getName() + "：" + num);
			condition1.signal();
			
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
	}
}
