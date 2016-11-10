package com.atguigu.test;

class Communication implements Runnable {
	int i = 1;

	public void run() {
		while (true) {
			synchronized (this) {
				notify();
				if (i <= 10) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + ":" + i++);
				} else{
					break;
				}
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
/**
 * 交替打印1-10
 * @author zxj
 *
 */
public class ThreadExe1{
	public static void main(String[] args) {
		Communication c = new Communication();
		
		Thread t1 = new Thread(c);
		t1.setName("线程1");
		t1.start();
		Thread t2 = new Thread(c);
		t2.setName("线程2");
		t2.start();
	}
}
