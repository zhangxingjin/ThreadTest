package com.atguigu.product;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProductTest {

	public static void main(String[] args) {
		Clerk clerk = new Clerk();
		final Productor p = new Productor(clerk);
		final Consumer c = new Consumer(clerk);

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					//sleep的原因是因为线程太快，会很容易生产到20个，sleep，就可以达到生产一部分，消费一部分
					try {
						Thread.sleep((long) (Math.random() * 1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					p.clerk.addProduct();
				}
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep((long) (Math.random() * 1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					c.clerk.getProduct();
				}
			}
		}).start();
	}

}

/**
 * 售货员
 * 
 * @author zxj
 * 
 */
class Clerk {

	private int product = 0;
	Lock lock = new ReentrantLock();
	Condition condition1 = lock.newCondition();
	Condition condition2 = lock.newCondition();

	// 生产者将产品生产，送往柜台
	public void addProduct() {

		lock.lock();

			try {
				if (product >= 20) {
					condition1.await();
				}
				product++;
				System.out.println("生产者生产了第" + product + "个产品");
				condition2.signal();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}

	}

	// 消费者到柜员这边消费产品
	public void getProduct() {

		lock.lock();

			try {
				if (this.product <= 0) {
					condition2.await();
				}
				product--;
				System.out.println("消费者取走了第" + (product + 1) + "个产品");
				condition1.signal();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}

	}

}

/**
 * 生产者
 * 
 * @author zxj
 * 
 */
class Productor {
	Clerk clerk;

	public Productor(Clerk clerk) {
		this.clerk = clerk;
	}
}

/**
 * 消费者
 * 
 * @author zxj
 * 
 */
class Consumer {
	Clerk clerk;

	public Consumer(Clerk clerk) {
		this.clerk = clerk;
	}
}
