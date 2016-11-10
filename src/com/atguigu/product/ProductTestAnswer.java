package com.atguigu.product;

public class ProductTestAnswer {

	public static void main(String[] args) {
		Clerk1 clerk = new Clerk1();
		Thread productorThread = new Thread(new Productor1(clerk));
		Thread consumerThread = new Thread(new Consumer1(clerk));
		productorThread.start();
		consumerThread.start();
	}

}

/**
 * 售货员
 * 
 * @author zxj
 * 
 */
class Clerk1 { // 售货员
	private int product = 0;

	public synchronized void addProduct() {
		if (product >= 20) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			product++;
			System.out.println("生产者生产了第" + product + "个产品");
			notifyAll();
		}
	}

	public synchronized void getProduct() {
		if (this.product <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("消费者取走了第" + product + "个产品");
			product--;
			notifyAll();
		}
	}
}

/**
 * 生产者
 * 
 * @author zxj
 * 
 */
class Productor1 implements Runnable { // 生产者
	Clerk1 clerk;

	public Productor1(Clerk1 clerk) {
		this.clerk = clerk;
	}

	public void run() {
		System.out.println("生产者开始生产产品");
		while (true) {
			try {
				Thread.sleep((int) Math.random() * 1000);
			} catch (InterruptedException e) {
			}
			clerk.addProduct();
		}
	}
}

/**
 * 消费者
 * 
 * @author zxj
 * 
 */
class Consumer1 implements Runnable { // 消费者
	Clerk1 clerk;

	public Consumer1(Clerk1 clerk) {
		this.clerk = clerk;
	}

	public void run() {
		System.out.println("消费者开始取走产品");
		while (true) {
			try {
				Thread.sleep((int) Math.random() * 1000);
			} catch (InterruptedException e) {
			}
			clerk.getProduct();
		}
	}
}
